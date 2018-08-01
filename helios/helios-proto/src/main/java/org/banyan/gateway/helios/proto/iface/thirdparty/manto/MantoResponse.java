package org.banyan.gateway.helios.proto.iface.thirdparty.manto;

import org.banyan.gateway.helios.common.ThirdpartyFee;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 数据源通用返回
 * @date 2018-06-27 18:30:16
 */
public class MantoResponse {

    private String wid;

    private String customerId;

    private String code; // 响应码

    private String status; // 状态

    private String message; // message

    private ThirdpartyFee thirdpartyFee;

    private MantoStatus mantoStatus;

    private Object result;

    public MantoResponse() {
    }

    public String getWid() {
        return wid;
    }

    public MantoResponse setWid(String wid) {
        this.wid = wid;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public MantoResponse setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public MantoResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MantoResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MantoResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ThirdpartyFee getThirdpartyFee() {
        return thirdpartyFee;
    }

    public MantoResponse setThirdpartyFee(ThirdpartyFee thirdpartyFee) {
        this.thirdpartyFee = thirdpartyFee;
        return this;
    }

    public MantoStatus getMantoStatus() {
        return mantoStatus;
    }

    public MantoResponse setMantoStatus(MantoStatus mantoStatus) {
        this.mantoStatus = mantoStatus;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public MantoResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
