package org.banyan.gateway.zues.message;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.common.SubmitCode.ArgsFailedDetail;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.banyan.gateway.helios.util.codec.MD5Util;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.gateway.zues.dto.Request;
import org.apache.commons.lang.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * Record validate 验证规则返回
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月02日 17:20:00
 */
public class Record {
    // 请求
    private Request request;
    // ip
    private String ip;
    // 账号
    private String account;
    // 产品
    private String product;
    // 是否是指标
    private boolean isPortrait = false;
    // 指标
    private Set<String> portraits;
    // 公钥
    private PublicKey publicKey;
    // banyan 私钥
    private PrivateKey privateKey;
    // msg
    private Message message;
    // 返回
    private Response response;
    // rsa
    private RSAUtil rsaUtil;
    // 用户请求参数
    private String decryptData;
    private ObjectNode objectNode;

    // 产品配置
    private HadesConfig hadesConfig;
    // 数据源配置
    private List<RouteConfig> routeConfigs;

    public Record(SubmitCode submitCode, RSAUtil rsaUtil) {
        this.rsaUtil = rsaUtil;
        this.message = new Message();
        this.response = new Response(submitCode);
        this.hadesConfig = new HadesConfig();
        this.routeConfigs = new ArrayList<>();
    }

    /**
     * 设置提交码
     * @param submitCode
     * @param argsFailedDetail
     * @return
     */
    public Record setSubmiCode(SubmitCode submitCode, ArgsFailedDetail argsFailedDetail) {
        String msg = null;
        if (null != argsFailedDetail) {
            msg = argsFailedDetail.getMsg();
        }
        setSubmiCode(submitCode, msg);
        return this;
    }

    public Record setSubmiCode(SubmitCode submitCode, String msg) {
        this.setSubmiCode(submitCode);
        if (SubmitCode.ARGS_FAILED == submitCode && StringUtils.isNotBlank(msg)) {
            this.response.setMessage(msg);
        }
        return this;
    }

    /**
     * 设置提交码
     * @param submitCode
     * @return
     */
    public Record setSubmiCode(SubmitCode submitCode) {
        response.setCode(submitCode.getCode());
        response.setStatus(submitCode.getStatus());
        response.setMessage(submitCode.getMessage());
        return this;
    }

    /**
     * 根据Message构造ResponseMessage
     */
    public Message buildMessage() {
        String data = JSON.toJSONString(response);
        if (null != publicKey) {
            data = rsaUtil.encrypt(publicKey, data);
            this.buildMessage(data, true);
        } else {
            this.buildMessage(data, false);
        }
        return message;
    }

    /**
     * 构造ResponseMessage
     * @param data
     * @param encrypt
     */
    private Message buildMessage(String data, boolean encrypt) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.DATA).append(data).append(Constants.ENCRYPT).append(encrypt);
        String sign = MD5Util.md5Upper(sb.toString());
        return message.setSign(sign).setEncrypt(encrypt).setData(data);
    }

    public String getGid() {
        return response.getGid();
    }

    public Record setGid(String gid) {
        this.response.setGid(gid);
        return this;
    }

    public String getCustomerId() {
        return response.getCustomerId();
    }

    public Record setCustomerId(String customerId) {
        this.response.setCustomerId(customerId);
        return this;
    }

    public Request getRequest() {
        return request;
    }

    public Record setRequest(Request request) {
        this.request = request;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Record setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Record setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public Record setProduct(String product) {
        this.product = product;
        return this;
    }

    public boolean isPortrait() {
        return isPortrait;
    }

    public Record setPortrait(boolean portrait) {
        isPortrait = portrait;
        return this;
    }

    public Set<String> getPortraits() {
        return portraits;
    }

    public Record setPortraits(Set<String> portraits) {
        this.portraits = portraits;
        return this;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public Record setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public Record setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public Record setMessage(Message message) {
        this.message = message;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public Record setResponse(Response response) {
        this.response = response;
        return this;
    }

    public RSAUtil getRsaUtil() {
        return rsaUtil;
    }

    public Record setRsaUtil(RSAUtil rsaUtil) {
        this.rsaUtil = rsaUtil;
        return this;
    }

    public String getDecryptData() {
        return decryptData;
    }

    public Record setDecryptData(String decryptData) {
        this.decryptData = decryptData;
        return this;
    }

    public ObjectNode getObjectNode() {
        return objectNode;
    }

    public Record setObjectNode(ObjectNode objectNode) {
        this.objectNode = objectNode;
        return this;
    }

    public HadesConfig getHadesConfig() {
        return hadesConfig;
    }

    public Record setHadesConfig(HadesConfig hadesConfig) {
        this.hadesConfig = hadesConfig;
        return this;
    }

    public List<RouteConfig> getRouteConfigs() {
        return routeConfigs;
    }

    public Record setRouteConfigs(List<RouteConfig> routeConfigs) {
        this.routeConfigs = routeConfigs;
        return this;
    }
}
