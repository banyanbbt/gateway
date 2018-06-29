package org.banyan.gateway.helios.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Fee
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月19日 16:57:00
 */
public class ProductFee implements Serializable {
    private static final long serialVersionUID = -5249967811229408794L;
    // 是否计费
    private Boolean fee = Boolean.FALSE;
    // 计费次数
    private int cnt = 0;
    // 计费总价
    private BigDecimal price;

    public ProductFee() {
    }

    public ProductFee(Boolean fee) {
        this.fee = fee;
    }

    public ProductFee(Boolean fee, BigDecimal price) {
        this.fee = fee;
        this.price = price;
    }

    public ProductFee(Boolean fee, int cnt) {
        this.fee = fee;
        this.cnt = cnt;
    }

    public ProductFee(Boolean fee, int cnt, BigDecimal price) {
        this.fee = fee;
        this.cnt = cnt;
        this.price = price;
    }

    public Boolean getFee() {
        return fee;
    }

    public ProductFee setFee(Boolean fee) {
        this.fee = fee;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductFee setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getCnt() {
        return cnt;
    }

    public ProductFee setCnt(int cnt) {
        this.cnt = cnt;
        return this;
    }
}
