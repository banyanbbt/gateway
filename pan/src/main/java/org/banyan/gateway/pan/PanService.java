package org.banyan.gateway.pan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.constant.RequestStatus;
import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.pan.MobileVerifyRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.pan.IPanService;
import org.banyan.gateway.helios.util.UUIDUtil;
import org.banyan.gateway.helios.util.codec.MD5Util;
import org.banyan.gateway.helios.util.codec.RSAUtil;
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
 * @desc 提供访问运营商数据的服务
 * @date 2016-12-15 14:23:24
 */
public class PanService implements IPanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanService.class);

    private PanProperties panProperties;

    private ThirdpartyRecordService thirdpartyRecordService;

    private RSAUtil rsaUtil;

    private static final String CODE_SUCCESS = "200";

    private static final Map<String, SubmitCode> VERIFY_MAPPING = new HashMap<>();

    static {
        VERIFY_MAPPING.put("1000", SubmitCode.VALIDATE_CONSISTENT);
        VERIFY_MAPPING.put("1001", SubmitCode.VALIDATE_INCONSISTENT);
        VERIFY_MAPPING.put("1002", SubmitCode.VALIDATE_UNSURE);
        VERIFY_MAPPING.put("1004", SubmitCode.VALIDATE_NO_RESULT);
    }

    public PanService(ThirdpartyRecordService thirdpartyRecordService, PanProperties panProperties, RSAUtil rsaUtil) {
        this.thirdpartyRecordService = thirdpartyRecordService;
        this.panProperties = panProperties;
        this.rsaUtil = rsaUtil;
    }

    @Override
    public BaseResponse mobileVerifyThree(MobileVerifyRequest mobileVerifyRequest) {
        ThirdpartyRecordMessage record = new ThirdpartyRecordMessage();
        record.setIface(Interface.BAIDU_GEO_CODER).setRequest(mobileVerifyRequest).setFee(Boolean.FALSE).setGid(mobileVerifyRequest.getGid());

        LOGGER.info("pan请求参数: {}", JSON.toJSONString(mobileVerifyRequest));

        Data data = new Data();
        data.setProductId("Y0206").setCustomerId(UUIDUtil.uuid()).setMobile(mobileVerifyRequest.getMobile()).setCid(mobileVerifyRequest.getCid()).setName(mobileVerifyRequest.getName());
        String encryptData = this.rsaUtil.encrypt(this.panProperties.getPublicKey(), JSON.toJSONString(data));

        Request request = new Request();
        String sign = MD5Util.md5Upper("account" + this.panProperties.getAccount() + "data" + encryptData);
        request.setAccount(this.panProperties.getAccount()).setData(encryptData).setSign(sign);

        // 发送请求
        String response = HttpRequest.post(this.panProperties.getBaseUrl(), JSON.toJSONString(request), Interface.YUNYINGSHANG_MOBILE_VERIFY_3);

        LOGGER.info("运营商手机号三要素认证查询结果: {}", response);

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
                if (result.getBoolean("encrypt")) {
                    // 数据是加密的
                    try {
                        JSONObject rs = JSON.parseObject(rsaUtil.decrypt(this.panProperties.getPrivateKey(), result.getString("data")));
                        String code = rs.getString("code");
                        if (CODE_SUCCESS.equals(code)) {
                            // 成功
                            baseResponse.setSubmitCode(VERIFY_MAPPING.get(code));
                            baseResponse.setFee(Boolean.TRUE);
                            record.setFee(Boolean.TRUE);
                        } else {
                            baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
                        }
                    } catch (Exception e) {
                        LOGGER.info("解密异常: ", e);
                        baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
                    }
                } else {
                    baseResponse.setSubmitCode(SubmitCode.CHANNEL_ERROR);
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

    static class Request {

        private String data;

        private String account;

        private String sign;

        public Request() {
        }

        public Request(String data, String account, String sign) {
            this.data = data;
            this.account = account;
            this.sign = sign;
        }

        public String getData() {
            return data;
        }

        public Request setData(String data) {
            this.data = data;
            return this;
        }

        public String getAccount() {
            return account;
        }

        public Request setAccount(String account) {
            this.account = account;
            return this;
        }

        public String getSign() {
            return sign;
        }

        public Request setSign(String sign) {
            this.sign = sign;
            return this;
        }
    }


    static class Data {

        private String productId;

        private String customerId;

        private String name;

        private String mobile;

        private String cid;

        public Data() {
        }

        public Data(String productId, String customerId, String name, String mobile, String cid) {
            this.productId = productId;
            this.customerId = customerId;
            this.name = name;
            this.mobile = mobile;
            this.cid = cid;
        }

        public String getProductId() {
            return productId;
        }

        public Data setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public String getCustomerId() {
            return customerId;
        }

        public Data setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public String getName() {
            return name;
        }

        public Data setName(String name) {
            this.name = name;
            return this;
        }

        public String getMobile() {
            return mobile;
        }

        public Data setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getCid() {
            return cid;
        }

        public Data setCid(String cid) {
            this.cid = cid;
            return this;
        }
    }
}
