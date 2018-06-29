package org.banyan.gateway.doris;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.constant.RequestStatus;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.doris.GeoCoderRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.doris.IDorisService;
import org.banyan.gateway.helios.util.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 提供访问百度的服务
 * @date 2016-12-15 14:23:24
 */
public class DorisService implements IDorisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DorisService.class);

    private static final String GEO_CODER = "/geocoder/v2";

    private DorisProperties dorisProperties;

    private ThirdpartyRecordService thirdpartyRecordService;

    public DorisService(ThirdpartyRecordService thirdpartyRecordService, DorisProperties dorisProperties) {
        this.thirdpartyRecordService = thirdpartyRecordService;
        this.dorisProperties = dorisProperties;
    }

    @Override
    public BaseResponse geoCoder(GeoCoderRequest request) {
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        // 百度geo免费
        record.setIface(Interface.BAIDU_GEO_CODER).setRequest(request).setFee(Boolean.FALSE).setGid(request.getGid());

        LOGGER.info("doris请求参数: {}", JSON.toJSONString(request));

        Map<String, String> param = new HashMap<>();
        param.put("address", request.getAddress());
        param.put("ak", this.dorisProperties.getAk());
        param.put("output", "json");

        // 发送请求
        String response = HttpRequest.get(this.dorisProperties.getBaseUrl() + GEO_CODER, param, Interface.BAIDU_GEO_CODER);

        LOGGER.info("百度geo coder查询结果: {}", response);

        // 组装返回结果
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(response).setStatus(RequestStatus.FAILED).setFee(Boolean.FALSE);

        record.setResp(response);

        if (null != response) {
            // 有返回结果
            JSONObject result = JSON.parseObject(response);
            baseResponse.setResponse(result);
            record.setResponse(result);
            if (null != result) {
                baseResponse.setStatus(RequestStatus.SUCCESS);
                int status = result.getInteger("status");
                if (0 == status && result.containsKey("result")) {
                    // 有数据
                    baseResponse.setSubmitCode(SubmitCode.QUERY_SUCCESS);
                } else {
                    // 没有查询到结果
                    baseResponse.setSubmitCode(SubmitCode.QUERY_NO_DATA);
                }
            } else {
                baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
            }
        } else {
            baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
        }

        // 向队列发送第三方请求记录
        this.thirdpartyRecordService.send(record);

        return baseResponse;
    }
}
