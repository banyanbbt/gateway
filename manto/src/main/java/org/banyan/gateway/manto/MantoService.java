package org.banyan.gateway.manto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.data.rabbitmq.dto.ThirdpartyRecordMessage;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.IMantoService;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoRequest;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoResponse;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoStatus;
import org.banyan.gateway.helios.util.StringUtil;
import org.banyan.gateway.helios.util.UniqueIdUtil;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.helios.util.http.HttpRequest;
import org.banyan.gateway.manto.feehandler.FeeHandlerFactory;
import org.banyan.gateway.manto.feehandler.IFeeHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 请求数据
 * @date 2018-06-27 18:30:20
 */
public class MantoService implements IMantoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MantoService.class);

    //帐号
    private String account;

    //请求地址
    private String baseUrl;

    private RSAUtil rsaUtil;

    //公钥
    private PublicKey publicKey;

    //己方私钥
    private PrivateKey privateKey;

    private ThirdpartyRecordService thirdpartyRecordService;

    public MantoService(String account, String baseUrl, RSAUtil rsaUtil, PublicKey publicKey,
                        PrivateKey privateKey, ThirdpartyRecordService thirdpartyRecordService) {
        this.account = account;
        this.baseUrl = baseUrl;
        this.rsaUtil = rsaUtil;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.thirdpartyRecordService = thirdpartyRecordService;
    }

    /**
     * 请求数据实现
     *
     * @param request
     * @return
     */
    @Override
    public MantoResponse callMantoService(MantoRequest request) {
        LOGGER.info("请求用户画像数据源开始:{}", JSONObject.toJSONString(request));

        String trackId = MDC.get(Constants.TRACKID);
        if (StringUtil.isBlank(trackId)) {
            // 如果未启用监控
            trackId = UniqueIdUtil.getUniqueId();
        }
        JSONObject jsonParams = new JSONObject().fluentPut(Constants.PRODUCT_ID, request.getProductId()).fluentPut(Constants.CUSTOMER_ID, trackId);
        request.getParameters().forEach((field, value) -> jsonParams.put(field.getName(), value));
        String encryptParams = rsaUtil.encrypt(this.publicKey, jsonParams.toString());
        String sign = DigestUtils.md5Hex(Constants.ACCOUNT + this.account + Constants.DATA + encryptParams);
        JSONObject dataJson = new JSONObject().fluentPut(Constants.ACCOUNT, this.account)
                .fluentPut(Constants.DATA, encryptParams).fluentPut(Constants.SIGN, sign);

        //请求黑曜石数据源
        String resp = HttpRequest.post(this.baseUrl, dataJson.toJSONString(), request.getInterfaze());
        LOGGER.info("用户画像数据源返回原始数据:{}", resp);

        MantoResponse mantoResponse = new MantoResponse().setMantoStatus(MantoStatus.REQUEST_FAILED)
                .setThirdpartyFee(new ThirdpartyFee(request.getInterfaze()));
        ThirdpartyRecordMessage message = new ThirdpartyRecordMessage().setIface(request.getInterfaze())
                .setGid(trackId).setFee(Boolean.FALSE).setRequest(request);
        try {
            if (null != resp) {
                JSONObject responseJson = JSONObject.parseObject(resp);
                if (null != responseJson) {
                    boolean encrypt = responseJson.getBooleanValue(Constants.ENCRYPT);
                    if (matchSign(responseJson, encrypt)) {
                        String respString = responseJson.getString(Constants.DATA);
                        if (encrypt) {
                            respString = rsaUtil.decrypt(this.privateKey, responseJson.getString(Constants.DATA));
                            LOGGER.info("解密后返回数据:{}", respString);
                        } else {
                            LOGGER.info("返回数据未加密:{}", respString);
                        }
                        JSONObject decryptRespJson = JSON.parseObject(respString);

                        mantoResponse.setCustomerId(decryptRespJson.getString(Constants.CUSTOMER_ID))
                                .setCode(decryptRespJson.getString(Constants.CODE))
                                .setWid(decryptRespJson.getString(Constants.GID))
                                .setMessage(decryptRespJson.getString(Constants.MESSAGE))
                                .setStatus(decryptRespJson.getString(Constants.STATUS))
                                .setResult(decryptRespJson.get(Constants.RESULT));

                        //处理并设置thirdPartyFee
                        IFeeHandler feeHandler = FeeHandlerFactory.getFeeHandler(request.getInterfaze());
                        ThirdpartyFee fee = feeHandler.handleMantoFee(mantoResponse, request.getInterfaze(), request.getInterfaceConfig(), request.getOperator());

                        mantoResponse.setMantoStatus(encrypt ? MantoStatus.SUCCESS : MantoStatus.DATA_NOT_ENCRYPT);
                        message.setResp(respString).setResponse(decryptRespJson).setFee(fee.getFee()).setPrice(fee.getPrice());
                    } else {
                        mantoResponse.setMantoStatus(MantoStatus.SIGN_NOT_EQUAL);
                        LOGGER.info("返回数据验签失败");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("JSON解析异常或数据解密异常", e);
        } finally {
            this.thirdpartyRecordService.send(message);
        }
        return mantoResponse;
    }

    /**
     * 对返回结果进行验签
     *
     * @param responseJson
     * @return
     */
    private boolean matchSign(JSONObject responseJson, boolean encrypt) {
        if (null == responseJson)
            return false;
        String sign = responseJson.getString(Constants.SIGN);
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.DATA).append(responseJson.get(Constants.DATA)).append(Constants.ENCRYPT).append(encrypt);
        String resultSign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
        return resultSign.equals(sign);
    }
}
