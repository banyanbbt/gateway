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
 *
 * @desc 产品分类表
 * @date 2018-01-10 09:55:27
 */
@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1773596327385273974L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    // 父分类
    @Column(name = "parent_id")
    private Integer parentId;
    // 产品分类唯一标记
    @Column(name = "code", nullable = false)
    private String code;
    // 产品分类名
    @Column(name = "name", nullable = false)
    private String name;
    // 产品分类描述
    @Column(name = "description")
    private String description;
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

    public ProductCategory setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ProductCategory setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ProductCategory setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductCategory setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategory setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ProductCategory setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProductCategory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ProductCategory setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
