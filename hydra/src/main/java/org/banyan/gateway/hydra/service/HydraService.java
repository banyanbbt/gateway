package org.banyan.gateway.hydra.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.ares.VehicleViolationRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.doris.GeoCoderRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.pan.MobileVerifyRequest;
import org.banyan.gateway.helios.proto.iface.product.hydra.IHydraService;
import org.banyan.gateway.helios.proto.iface.thirdparty.ares.IAresService;
import org.banyan.gateway.helios.proto.iface.thirdparty.doris.IDorisService;
import org.banyan.gateway.helios.proto.iface.thirdparty.pan.IPanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 画像服务service
 * @date 2018-02-27 16:35:00
 */
@Service
public class HydraService implements IHydraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HydraService.class);

    @Autowired
    private IDorisService dorisService;
    @Autowired
    private IAresService aresService;
    @Autowired
    private IPanService panService;

    @Override
    public ProductResponse geoCoder(GeoCoderRequest request) {
        LOGGER.info("geo coder 请求参数: {}", JSON.toJSONString(request));
        BaseResponse baseResponse = this.dorisService.geoCoder(request);

        SubmitCode code = baseResponse.getSubmitCode();
        LOGGER.info("第三方返回code: {}", code);
        ProductResponse response = new ProductResponse();
        if (SubmitCode.QUERY_SUCCESS == code) {
            // 有数据
            JSONObject location = baseResponse.getResponse().getJSONObject("result").getJSONObject("location");
            Map<String, String> result = new HashMap<>();
            result.put("lat", location.getString("lat"));
            result.put("lng", location.getString("lng"));
            response.setResult(result);
        }
        // 默认不收费
        response.setProductFee(new ProductFee());
        response.setSubmitCode(code).addThirdpartyFees(new ThirdpartyFee(baseResponse.getFee(), baseResponse.getPrice(), Interface.BAIDU_GEO_CODER));
        return response;
    }

    @Override
    public ProductResponse vehicleViolation(VehicleViolationRequest request) {
        LOGGER.info("vehicle violation 请求参数: {}", JSON.toJSONString(request));
        BaseResponse baseResponse = this.aresService.vehicleViolation(request);

        SubmitCode code = baseResponse.getSubmitCode();
        LOGGER.info("第三方返回code: {}", code);
        ProductResponse response = new ProductResponse();
        if (SubmitCode.QUERY_SUCCESS == code) {
            // 有数据
            response.setResult(baseResponse.getResponse().getJSONObject("result"));
            response.setProductFee(new ProductFee(Boolean.TRUE));
        } else {
            // 无数据或者渠道异常
            response.setProductFee(new ProductFee(Boolean.FALSE));
        }
        response.setSubmitCode(code).addThirdpartyFees(new ThirdpartyFee(baseResponse.getFee(), baseResponse.getPrice(), Interface.JUHESHUJU_VEHICLE_VIOLATIO));
        return response;
    }

    @Override
    public ProductResponse mobileVerifyThree(MobileVerifyRequest request) {
        LOGGER.info("运营商手机号三要素请求参数: {}", JSON.toJSONString(request));
        BaseResponse baseResponse = this.panService.mobileVerifyThree(request);

        SubmitCode code = baseResponse.getSubmitCode();
        LOGGER.info("第三方返回code: {}", code);
        ProductResponse response = new ProductResponse();
        if (SubmitCode.CHANNEL_ERROR != code) {
            // 有数据
            response.setProductFee(new ProductFee(Boolean.TRUE));
        } else {
            // 无数据或者渠道异常
            response.setProductFee(new ProductFee(Boolean.FALSE));
        }
        response.setSubmitCode(code).addThirdpartyFees(new ThirdpartyFee(baseResponse.getFee(), baseResponse.getPrice(), Interface.YUNYINGSHANG_MOBILE_VERIFY_3));
        return response;
    }
}
