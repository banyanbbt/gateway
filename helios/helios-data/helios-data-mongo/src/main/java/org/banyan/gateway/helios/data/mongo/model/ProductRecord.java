package org.banyan.gateway.helios.data.mongo.model;

import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductRecord
 * 系统请求记录
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月20日 20:44:00
 */
@Document(collection = "product_record")
public class ProductRecord extends MongoRecord {
    private static final long serialVersionUID = -5883569854710510646L;
    // 请求报文
    @Field("request")
    private Object request;
    // 返回报文
    @Field("response")
    private Object response;
    // ip
    @Field("ip")
    private String ip;
    // 请求账号
    @Indexed
    @Field("account")
    private String account;
    // 产品
    @Indexed
    @Field("product")
    private String product;
    // 请求报文解密字符串
    @Field("request_body")
    private String requestBody;

    // 返回报文详情
    @Indexed
    @Field("code")
    private String code;
    @Indexed
    @Field("status")
    private String status;
    @Field("message")
    private String message;
    @Field("result")
    private Object result;
    @Indexed
    @Field("customer_id")
    private String customerId;
    @Indexed
    @Field("gid")
    private String gid;
    @Field("configs")
    private Map<String, String> configs;

    // 第三方数据源
    @Indexed
    @Field("interfaces")
    private Set<String> interfaces;
    // 产品计费
    @Field("product_fee")
    private ProductFee productFee;
    // 第三方成本
    @Field("thirdparty_fees")
    private List<ThirdpartyFee> thirdpartyFees;

    public ProductRecord() {
        super();
    }

    public Object getRequest() {
        return request;
    }

    public ProductRecord setRequest(Object request) {
        this.request = request;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public ProductRecord setResponse(Object response) {
        this.response = response;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ProductRecord setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public ProductRecord setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public ProductRecord setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public ProductRecord setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ProductRecord setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProductRecord setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ProductRecord setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ProductRecord setResult(Object result) {
        this.result = result;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ProductRecord setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getGid() {
        return gid;
    }

    public ProductRecord setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public ProductRecord setConfigs(Map<String, String> configs) {
        this.configs = configs;
        return this;
    }

    public Set<String> getInterfaces() {
        return interfaces;
    }

    public ProductRecord setInterfaces(Set<String> interfaces) {
        this.interfaces = interfaces;
        return this;
    }

    public ProductFee getProductFee() {
        return productFee;
    }

    public ProductRecord setProductFee(ProductFee productFee) {
        this.productFee = productFee;
        return this;
    }

    public List<ThirdpartyFee> getThirdpartyFees() {
        return thirdpartyFees;
    }

    public ProductRecord setThirdpartyFees(List<ThirdpartyFee> thirdpartyFees) {
        this.thirdpartyFees = thirdpartyFees;
        return this;
    }
}
