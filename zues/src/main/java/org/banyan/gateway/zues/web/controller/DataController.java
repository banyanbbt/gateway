package org.banyan.gateway.zues.web.controller;

import org.apache.commons.lang.time.StopWatch;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ProductRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ProductRecordService;
import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.iface.product.hades.IRouteService;
import org.banyan.gateway.helios.util.StringUtil;
import org.banyan.gateway.helios.util.UniqueIdUtil;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.zues.dto.Request;
import org.banyan.gateway.zues.message.Message;
import org.banyan.gateway.zues.message.Record;
import org.banyan.gateway.zues.message.Response;
import org.banyan.gateway.zues.service.ValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 数据请求接口
 * @date 2018-01-11 18:50:17
 */
@Controller
@RequestMapping("**")
public class DataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private RSAUtil rsaUtil;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ValidateService validateService;
    @Autowired
    private IRouteService routeService;
    @Autowired
    private ProductRecordService productRecordService;

    /**
     * 后台统一入口api, 负责验证和向后续服务请求数据以及对接监控
     * 01. 生成全局一次请求的trackId
     * 02. 生成业务gid
     * 03. 请求验证
     * 04. 请求数据
     * 05. 对接监控
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message credit(@RequestBody Request request) {
        StopWatch sw = new StopWatch();
        sw.start();
        // 生成gid, 作用于trackId
        String gid = UniqueIdUtil.getUniqueId();

        LOGGER.info("请求开始处理...");

        String ip = this.getRequestIp();
        Record record = new Record(SubmitCode.SYSTEM_ERROR, rsaUtil);
        record.setGid(gid).setIp(ip).setRequest(request);

        LOGGER.info("请求ip: {}", ip);
        ProductResponse response = null;
        try {
            // 验证url是否为/data/credit
            String url = this.httpServletRequest.getRequestURI();
            if (!url.equalsIgnoreCase("/api") || null == request) {
                // 该产品只有一个url资源，可以写死
                LOGGER.info("当前url: {}", url);
                record.setSubmiCode(SubmitCode.PATH_FAILED);
            } else if (this.validateService.validateRequest(record)) {
                // 正常义务逻辑
                response = routeService.invoke(record.getObjectNode().toString(), record.getHadesConfig(), record.getRouteConfigs());
                LOGGER.info("路由服务返回: {}", response.getSubmitCode());
                record.setSubmiCode(response.getSubmitCode(), response.getArgsFailedDetail());
                record.getResponse().setResult(response.getResult());
            }
        } catch (Exception e) {
            LOGGER.info("系统异常", e);
            record.setSubmiCode(SubmitCode.SYSTEM_ERROR);
        } finally {
            Response resp = record.getResponse();
            ProductRecordMessage productRecordMessage = new ProductRecordMessage()
                    .setRequest(record.getRequest())
                    .setResponse(resp)
                    .setIp(record.getIp())
                    .setAccount(record.getAccount())
                    .setProduct(record.getProduct())
                    .setRequestBody(record.getDecryptData())
                    .setCustomerId(record.getCustomerId())
                    .setGid(record.getGid())
                    .setCode(resp.getCode())
                    .setStatus(resp.getStatus())
                    .setMessage(resp.getMessage())
                    .setResult(resp.getResult())
                    .setConfigs(record.getHadesConfig());
            if (null != response) {
                productRecordMessage.setProductFee(response.getProductFee())
                        .setThirdpartyFees(response.getThirdpartyFees());
            }
            productRecordService.send(productRecordMessage);

            sw.stop();
            long time = sw.getTime();
            String account = record.getAccount();
            String product = record.getProduct();
            LOGGER.info("账户{}请求产品{}, 耗时{}ms", account, product, time);
        }

        LOGGER.info("请求结束...");

        return record.buildMessage();
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public Message credit() {
        return credit(null);
    }

    /**
     * 获取请求的ip
     * @return
     */
    private String getRequestIp() {
        try {
            String ip = this.httpServletRequest.getHeader("X-Forwarded-For");
            if (ip != null && ip.contains(Constants.POINT)) {
                return ip.contains(Constants.COMMA) ? ip.split(Constants.COMMA)[0] : ip;
            }

            ip = this.httpServletRequest.getHeader("Proxy-Client-IP");
            if (StringUtil.isBlank(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = this.httpServletRequest.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtil.isBlank(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = this.httpServletRequest.getRemoteAddr();
            }
            return ip;

        } catch (Throwable e) {
            return Constants.EMPTY_STR;
        }
    }

}
