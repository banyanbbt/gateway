package org.banyan.gateway.helios.data.rabbitmq.dto;

import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.ThirdpartyFee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductRecordMessage
 * 系统请求记录
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月20日 11:29:00
 */
public class ProductRecordMessage implements Serializable {
    private static final long serialVersionUID = 1422554316346344921L;
    // 请求报文
    private Object request;
    // 返回报文
    private Object response;
    // ip
    private String ip;
    // 请求账号
    private String account;
    // 产品id
    private String product;
    // 请求报文解密字符串
    private String requestBody;

    // 返回报文详情
    private String code;
    private String status;
    private String message;
    private Object result;
    private String customerId;
    private String gid;
    private Map<String, String> configs;

    // 产品计费
    private ProductFee productFee;
    // 第三方成本
    private List<ThirdpartyFee> thirdpartyFees;

    // 创建时间
    private Date createTime;

    public ProductRecordMessage() {
        this.createTime = new Date();
    }

    public Object getRequest() {
        return request;
    }

    public ProductRecordMessage setRequest(Object request) {
        this.request = request;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public ProductRecordMessage setResponse(Object response) {
        this.response = response;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ProductRecordMessage setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public ProductRecordMessage setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public ProductRecordMessage setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public ProductRecordMessage setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ProductRecordMessage setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProductRecordMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ProductRecordMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ProductRecordMessage setResult(Object result) {
        this.result = result;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ProductRecordMessage setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getGid() {
        return gid;
    }

    public ProductRecordMessage setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public ProductRecordMessage setConfigs(Map<String, String> configs) {
        this.configs = configs;
        return this;
    }

    public ProductFee getProductFee() {
        return productFee;
    }

    public ProductRecordMessage setProductFee(ProductFee productFee) {
        this.productFee = productFee;
        return this;
    }

    public List<ThirdpartyFee> getThirdpartyFees() {
        return thirdpartyFees;
    }

    public ProductRecordMessage setThirdpartyFees(List<ThirdpartyFee> thirdpartyFees) {
        this.thirdpartyFees = thirdpartyFees;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductRecordMessage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
