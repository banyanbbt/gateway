package org.banyan.gateway.helios.data.rabbitmq.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.banyan.gateway.helios.common.Interface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ThirdpartyRecordMessage
 * 第三方请求记录
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月19日 19:59:00
 */
public class ThirdpartyRecordMessage implements Serializable {
    private static final long serialVersionUID = -1709006479523611920L;
    private String gid;
    // 请求对象
    private Object request;
    // 返回字符串
    private String resp;
    // 返回对象
    private Object response;
    // 是否计费
    private Boolean fee;
    // 数据源接口
    @JsonDeserialize(as = String.class)
    private String iface;
    // 计费价格
    private BigDecimal price;
    // 请求字段
    private Date createTime;

    public ThirdpartyRecordMessage() {
        this.createTime = new Date();
    }

    public ThirdpartyRecordMessage(String gid, Object request, String resp, Object response, Boolean fee, Interface iface, BigDecimal price) {
        this();
        this.gid = gid;
        this.request = request;
        this.resp = resp;
        this.response = response;
        this.fee = fee;
        if (null != iface) {
            this.iface = iface.getIface();
        }
        this.price = price;
    }

    public String getGid() {
        return gid;
    }

    public ThirdpartyRecordMessage setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public Object getRequest() {
        return request;
    }

    public ThirdpartyRecordMessage setRequest(Object request) {
        this.request = request;
        return this;
    }

    public String getResp() {
        return resp;
    }

    public ThirdpartyRecordMessage setResp(String resp) {
        this.resp = resp;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public ThirdpartyRecordMessage setResponse(Object response) {
        this.response = response;
        return this;
    }

    public Boolean getFee() {
        return fee;
    }

    public ThirdpartyRecordMessage setFee(Boolean fee) {
        this.fee = fee;
        return this;
    }

    public String getIface() {
        return iface;
    }

    public ThirdpartyRecordMessage setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public ThirdpartyRecordMessage setIface(Interface iface) {
        if (null != iface) {
            this.iface = iface.getIface();
        }
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ThirdpartyRecordMessage setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ThirdpartyRecordMessage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

}
