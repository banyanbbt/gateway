package org.banyan.gateway.helios.data.jpa.domain.product;

import org.banyan.gateway.helios.data.jpa.converter.HeliosDataJpaConverters;
import org.banyan.gateway.helios.data.jpa.domain.pk.ProductConfigPk;
import org.banyan.gateway.helios.data.jpa.model.ConfigPolicy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * 产品配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月01日 16:09:00
 */
@Entity
@Table(name = "product_config")
@IdClass(ProductConfigPk.class)
public class ProductConfig implements Serializable {
    private static final long serialVersionUID = -2641997371805678923L;
    // 产品列表
    @Id
    @Column(name = "product", nullable = false)
    private String product;
    // 配置键
    @Id
    @Column(name = "config", nullable = false)
    private String config;
    // 账户状态
    @Column(name = "policy", nullable = false, columnDefinition = "ENUM('NONE','REPLACE','CONTAINS')")
    @Convert(converter = HeliosDataJpaConverters.ConfigPolicyConverter.class)
    private ConfigPolicy policy;
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
    private Date updateTime;

    public String getConfig() {
        return config;
    }

    public ProductConfig setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public ProductConfig setProduct(String product) {
        this.product = product;
        return this;
    }

    public ConfigPolicy getPolicy() {
        return policy;
    }

    public ProductConfig setPolicy(ConfigPolicy policy) {
        this.policy = policy;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ProductConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ProductConfig setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductConfig setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProductConfig setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
