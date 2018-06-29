package org.banyan.gateway.helios.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ThirdpartyFee
 * 第三方计费
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月16日 15:12:00
 */
public class ThirdpartyFee implements Serializable {
    private static final long serialVersionUID = 7188204626658705845L;
    // 是否计费
    private Boolean fee = Boolean.FALSE;
    // 计费总价
    private BigDecimal price;
    // 数据源
    private String iface;

    public ThirdpartyFee() {
    }

    public ThirdpartyFee(Interface iface) {
        this.iface = iface.getIface();
    }

    public ThirdpartyFee(Boolean fee, Interface iface) {
        this(fee, null, iface);
    }

    public ThirdpartyFee(Boolean fee, BigDecimal price, Interface iface) {
        this.fee = fee;
        this.price = price;
        this.iface = iface.getIface();
    }

    public Boolean getFee() {
        return fee;
    }

    public ThirdpartyFee setFee(Boolean fee) {
        this.fee = fee;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ThirdpartyFee setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getIface() {
        return iface;
    }

    public ThirdpartyFee setIface(String iface) {
        this.iface = iface;
        return this;
    }
}
