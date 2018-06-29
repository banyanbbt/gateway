package org.banyan.gateway.helios.proto.dto.thirdparty;

import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.common.SubmitCode.ArgsFailedDetail;
import org.banyan.gateway.helios.proto.constant.RequestStatus;

import java.math.BigDecimal;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 基础第三方数据源返回dto
 * @date 2018-01-10 17:24:10
 */
public class BaseResponse {

    // 如果是null表示请求失败，渠道异常
    private JSONObject response;
    // 原始返回字符串
    private String content;
    // 请求状态
    private RequestStatus status;
    // 解析出的返回码
    private SubmitCode submitCode;
    // error msg
    private ArgsFailedDetail errorMsg;
    // 是否计成本
    private Boolean fee;
    // 成本价格
    private BigDecimal price;

    public BaseResponse() {
    }

    public BaseResponse(JSONObject response, String content, RequestStatus status, SubmitCode submitCode) {
        this.response = response;
        this.content = content;
        this.status = status;
        this.submitCode = submitCode;
    }

    public BaseResponse(JSONObject response, String content, RequestStatus status, SubmitCode submitCode, ArgsFailedDetail errorMsg) {
        this.response = response;
        this.content = content;
        this.status = status;
        this.submitCode = submitCode;
        this.errorMsg = errorMsg;
    }

    public BaseResponse(JSONObject response, String content, RequestStatus status, SubmitCode submitCode, ArgsFailedDetail errorMsg, Boolean fee) {
        this.response = response;
        this.content = content;
        this.status = status;
        this.submitCode = submitCode;
        this.errorMsg = errorMsg;
        this.fee = fee;
    }

    public BaseResponse(JSONObject response, String content, RequestStatus status, SubmitCode submitCode, ArgsFailedDetail errorMsg, Boolean fee, BigDecimal price) {
        this.response = response;
        this.content = content;
        this.status = status;
        this.submitCode = submitCode;
        this.errorMsg = errorMsg;
        this.fee = fee;
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public BaseResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public JSONObject getResponse() {
        return response;
    }

    public BaseResponse setResponse(JSONObject response) {
        this.response = response;
        return this;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public BaseResponse setStatus(RequestStatus status) {
        this.status = status;
        return this;
    }

    public SubmitCode getSubmitCode() {
        return submitCode;
    }

    public BaseResponse setSubmitCode(SubmitCode submitCode) {
        this.submitCode = submitCode;
        return this;
    }

    public SubmitCode.ArgsFailedDetail getErrorMsg() {
        return errorMsg;
    }

    public BaseResponse setErrorMsg(ArgsFailedDetail errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public Boolean getFee() {
        return fee;
    }

    public BaseResponse setFee(Boolean fee) {
        this.fee = fee;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BaseResponse setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
