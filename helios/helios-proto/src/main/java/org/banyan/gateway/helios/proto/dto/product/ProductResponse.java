package org.banyan.gateway.helios.proto.dto.product;

import org.banyan.gateway.helios.common.ProductFee;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.common.SubmitCode.ArgsFailedDetail;
import org.banyan.gateway.helios.common.ThirdpartyFee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * HadesResponse
 * 基础的返回
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月09日 16:35:00
 */
public class ProductResponse implements Serializable {
    private static final long serialVersionUID = -7684153279452077015L;
    // 提交码
    private SubmitCode submitCode;
    // 错误代码
    private ArgsFailedDetail argsFailedDetail;
    // 返回结果
    private Object result;
    // 产品计费
    private ProductFee productFee;
    // 第三方成本
    private List<ThirdpartyFee> thirdpartyFees;

    public ProductResponse() {
    }

    public ProductResponse(SubmitCode submitCode) {
        this(submitCode, null);
    }

    public ProductResponse(SubmitCode submitCode, Object result) {
        this(submitCode, null, result);
    }

    public ProductResponse(SubmitCode submitCode, ArgsFailedDetail argsFailedDetail, Object result) {
        this.submitCode = submitCode;
        this.argsFailedDetail = argsFailedDetail;
        this.result = result;
    }

    public SubmitCode getSubmitCode() {
        return submitCode;
    }

    public ProductResponse setSubmitCode(SubmitCode submitCode) {
        this.submitCode = submitCode;
        return this;
    }

    public ArgsFailedDetail getArgsFailedDetail() {
        return argsFailedDetail;
    }

    public ProductResponse setArgsFailedDetail(ArgsFailedDetail argsFailedDetail) {
        this.argsFailedDetail = argsFailedDetail;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ProductResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public ProductFee getProductFee() {
        return productFee;
    }

    public ProductResponse setProductFee(ProductFee productFee) {
        this.productFee = productFee;
        return this;
    }

    public List<ThirdpartyFee> getThirdpartyFees() {
        return thirdpartyFees;
    }

    public synchronized ProductResponse addThirdpartyFees(ThirdpartyFee thirdpartyFee) {
        if (null == this.thirdpartyFees) {
            this.thirdpartyFees = new ArrayList<>();
        }
        this.thirdpartyFees.add(thirdpartyFee);
        return this;
    }

    public ProductResponse setThirdpartyFees(List<ThirdpartyFee> thirdpartyFees) {
        this.thirdpartyFees = thirdpartyFees;
        return this;
    }
}
