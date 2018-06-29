package org.banyan.gateway.helios.data.jpa.domain.account;

import org.banyan.gateway.helios.data.jpa.domain.pk.AccountConfigPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * AccountConfig 账号配置
 *
 * @author Kevin Huang
 * @since version
 *
 * 2018年03月01日 16:55:00
 */
@Entity
@Table(name = "account_config")
@IdClass(AccountConfigPk.class)
public class AccountConfig implements Serializable {
    private static final long serialVersionUID = 3996827688495747019L;
    @Id
    @Column(name = "account", nullable = false)
    private String account;
    // 配置键
    @Id
    @Column(name = "config", nullable = false)
    private String config;
    // 产品列表
    @Id
    @Column(name = "product", nullable = false)
    private String product;
    // 配置值
    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;
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

    public AccountConfig setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getConfig() {
        return config;
    }

    public AccountConfig setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public AccountConfig setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AccountConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public AccountConfig setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AccountConfig setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AccountConfig setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
