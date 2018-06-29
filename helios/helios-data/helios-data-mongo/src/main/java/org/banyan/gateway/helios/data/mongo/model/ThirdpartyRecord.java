package org.banyan.gateway.helios.data.mongo.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ThirdpartyRecord
 * 第三方请求的记录
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月19日 16:29:00
 */
@Document(collection = "third_party_record")
public class ThirdpartyRecord extends MongoRecord {
    private static final long serialVersionUID = -4775293427677408996L;
    @Indexed
    @Field("gid")
    private String gid;
    // 请求对象
    @Field("request")
    private Object request;
    // 返回字符串
    @Field("resp")
    private String resp;
    // 返回对象
    @Field("response")
    private Object response;
    // 是否计费
    @Field("fee")
    private Boolean fee;
    // 数据源接口
    @Field("iface")
    private String iface;
    // 计费价格
    @Field("price")
    private BigDecimal price;

    public ThirdpartyRecord() {
        super();
    }

    public String getGid() {
        return gid;
    }

    public ThirdpartyRecord setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public Object getRequest() {
        return request;
    }

    public ThirdpartyRecord setRequest(Object request) {
        this.request = request;
        return this;
    }

    public String getResp() {
        return resp;
    }

    public ThirdpartyRecord setResp(String resp) {
        this.resp = resp;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public ThirdpartyRecord setResponse(Object response) {
        this.response = response;
        return this;
    }

    public Boolean getFee() {
        return fee;
    }

    public ThirdpartyRecord setFee(Boolean fee) {
        this.fee = fee;
        return this;
    }

    public String getIface() {
        return iface;
    }

    public ThirdpartyRecord setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ThirdpartyRecord setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

}
