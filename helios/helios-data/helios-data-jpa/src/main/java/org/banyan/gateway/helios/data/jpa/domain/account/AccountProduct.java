package org.banyan.gateway.helios.data.jpa.domain.account;

import org.banyan.gateway.helios.data.jpa.domain.pk.AccountProductPk;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户产品表
 * @date 2018-01-03 10:34:40
 */
@Entity
@Table(name = "account_product")
@IdClass(AccountProductPk.class)
public class AccountProduct implements Serializable {
    private static final long serialVersionUID = -3313490844044367246L;
    @Id
    @Column(name = "account", nullable = false)
    private String account;
    // 产品标记
    @Id
    @Column(name = "product", nullable = false)
    private String product;
    // 画像计费，最高价
    @Column(name = "highest_price")
    private BigDecimal highestPrice;
    // 画像计费，最低价
    @Column(name = "lowest_price")
    private BigDecimal lowestPrice;
    // 账户对应产品的价格，不同于基础价格
    @Column(name = "price")
    private BigDecimal price;
    // 限制使用次数, -1表示不做限制
    @Column(name = "limit_count", nullable = false)
    private int limitCount;
    // 已经使用次数
    @Column(name = "used_count", nullable = false)
    private int usedCount;
    // 生效时间
    @Column(name = "effective_at")
    private Date effectiveAt;
    // 过期时间
    @Column(name = "expire_at")
    private Date expireAt;
    // 是否可用, 0表示不可用，1表示可用
    @Column(name = "status", nullable = false)
    private boolean status;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getAccount() {
        return account;
    }

    public AccountProduct setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public AccountProduct setProduct(String product) {
        this.product = product;
        return this;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public AccountProduct setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
        return this;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public AccountProduct setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AccountProduct setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public AccountProduct setLimitCount(int limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public AccountProduct setUsedCount(int usedCount) {
        this.usedCount = usedCount;
        return this;
    }

    public Date getEffectiveAt() {
        return effectiveAt;
    }

    public AccountProduct setEffectiveAt(Date effectiveAt) {
        this.effectiveAt = effectiveAt;
        return this;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public AccountProduct setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public AccountProduct setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public AccountProduct setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AccountProduct setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AccountProduct setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
