package org.banyan.gateway.helios.data.jpa.domain.product;

import org.banyan.gateway.helios.data.jpa.converter.HeliosDataJpaConverters;
import org.banyan.gateway.helios.data.jpa.model.ProductFeeType;
import org.banyan.gateway.helios.data.jpa.model.SourceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 产品表
 * @date 2018-01-03 10:31:16
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = -7124703064720380746L;
    // 产品标记
    @Id
    @Column(name = "product", nullable = false)
    private String product;
    // 分类
    @Column(name = "category_id")
    private Integer categoryId;
    // 计费类型
    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    @Convert(converter = HeliosDataJpaConverters.ProductFeeTypeConverter.class)
    private ProductFeeType feeType;
    // 产品名
    @Column(name = "name", nullable = false)
    private String name;
    // 产品描述
    @Column(name = "description")
    private String description;
    // 产品服务方法
    @Column(name = "service_method")
    private String serviceMethod;
    // 产品基础定价
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    // 产品数据来源
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    @Convert(converter = HeliosDataJpaConverters.SourceTypeConverter.class)
    private SourceType sourceType;
    // 产品是否可用, 0表示不可用，1表示可用
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

    public String getProduct() {
        return product;
    }

    public Product setProduct(String product) {
        this.product = product;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Product setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public Product setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public Product setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public Product setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Product setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Product setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Product setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public ProductFeeType getFeeType() {
        return feeType;
    }

    public Product setFeeType(ProductFeeType feeType) {
        this.feeType = feeType;
        return this;
    }
}
