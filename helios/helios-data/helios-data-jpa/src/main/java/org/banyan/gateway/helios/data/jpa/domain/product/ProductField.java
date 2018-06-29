package org.banyan.gateway.helios.data.jpa.domain.product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 产品需要的参数表
 * @date 2018-01-03 11:10:43
 */
@Entity
@Table(name = "product_field")
public class ProductField implements Serializable {
    private static final long serialVersionUID = -4453109651867851644L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    // 产品标记
    @Column(name = "product", nullable = false)
    private String product;
    // 参数名
    @Column(name = "field", nullable = false)
    private String field;
    // 参数名
    @Column(name = "parent_id")
    private Integer parentId;
    // 是否必须
    @Column(name = "required", nullable = false)
    private boolean required;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public ProductField setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public ProductField setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getField() {
        return field;
    }

    public ProductField setField(String field) {
        this.field = field;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ProductField setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public ProductField setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ProductField setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductField setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProductField setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
