package org.banyan.gateway.zues.message;

import org.banyan.gateway.helios.common.SubmitCode;

import java.io.Serializable;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 接口返回的具体信息
 * @date 2017-05-11 09:15:44
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 3528804732111791402L;

    private String code;

    private String status;

    private String message;

    private Object result;

    private String customerId;

    private String gid;

    public Response() {

    }

    public Response(SubmitCode submitCode) {
        this.code = submitCode.getCode();
        this.status = submitCode.getStatus();
        this.message = submitCode.getMessage();
    }

    public Response(SubmitCode submitCode, Object result) {
        this.code = submitCode.getCode();
        this.status = submitCode.getStatus();
        this.message = submitCode.getMessage();
        this.result = result;
    }

    public Response(Response response) {
        super();
        this.code = response.getCode();
        this.status = response.getStatus();
        this.message = response.getMessage();
        this.result = response.getResult();
    }

    public String getCode() {
        return code;
    }

    public Response setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Response setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Response setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getGid() {
        return gid;
    }

    public Response setGid(String gid) {
        this.gid = gid;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public Response setResult(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response message1 = (Response) o;

        if (code != null ? !code.equals(message1.code) : message1.code != null) return false;
        if (status != null ? !status.equals(message1.status) : message1.status != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (result != null ? !result.equals(message1.result) : message1.result != null) return false;
        if (customerId != null ? !customerId.equals(message1.customerId) : message1.customerId != null) return false;
        return gid != null ? gid.equals(message1.gid) : message1.gid == null;
    }

    @Override
    public int hashCode() {
        int result1 = code != null ? code.hashCode() : 0;
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        result1 = 31 * result1 + (message != null ? message.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (customerId != null ? customerId.hashCode() : 0);
        result1 = 31 * result1 + (gid != null ? gid.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", customerId='" + customerId + '\'' +
                ", gid='" + gid + '\'' +
                '}';
    }
}
