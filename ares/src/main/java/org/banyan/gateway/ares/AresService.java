package org.banyan.gateway.ares;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.constant.RequestStatus;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.ares.VehicleViolationRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.ares.IAresService;
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
 * @desc 提供访问聚合数据的服务
 * @date 2016-12-15 14:23:24
 */
public class AresService implements IAresService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AresService.class);

    private static final String VEHICLE_VIOLATIO = "/wz/query";

    private static final String SUCCESS = "200";

    private AresProperties aresProperties;

    private ThirdpartyRecordService thirdpartyRecordService;

    public AresService(ThirdpartyRecordService thirdpartyRecordService, AresProperties aresProperties) {
        this.thirdpartyRecordService = thirdpartyRecordService;
        this.aresProperties = aresProperties;
    }

    @Override
    public BaseResponse vehicleViolation(VehicleViolationRequest request) {
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(Interface.BAIDU_GEO_CODER).setRequest(request).setFee(Boolean.FALSE).setGid(request.getGid());

        LOGGER.info("ares请求参数: {}", JSON.toJSONString(request));

        Map<String, String> param = new HashMap<>();
        param.put("key", this.aresProperties.getKey());
        param.put("city", request.getCity());
        param.put("hphm", request.getPlateNo());
        param.put("hpzl", request.getPlateType());
        param.put("engineno", request.getEngineNo());
        param.put("classno", request.getVin());

        // 发送请求
        String response = HttpRequest.get(this.aresProperties.getBaseUrl() + VEHICLE_VIOLATIO, param, Interface.JUHESHUJU_VEHICLE_VIOLATIO);

        LOGGER.info("聚合数据vehicle violation查询结果: {}", response);

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
                String resultCode = result.getString("resultcode");
                if (SUCCESS.equals(resultCode)) {
                    // 有数据
                    baseResponse.setSubmitCode(SubmitCode.QUERY_SUCCESS);
                    record.setFee(Boolean.TRUE);
                    baseResponse.setFee(Boolean.TRUE);
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
