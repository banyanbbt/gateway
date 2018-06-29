package org.banyan.gateway.helios.data.jpa.domain.portrait;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 画像分类
 * @date 2018-01-10 14:59:43
 */
@Entity
@Table(name = "portrait_category")
public class PortraitCategory implements Serializable {
    private static final long serialVersionUID = -6336220039142559257L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    // 父分类
    @Column(name = "parent_id")
    private Integer parentId;
    // 画像分类唯一标记
    @Column(name = "code", nullable = false)
    private String code;
    // 画像分类名
    @Column(name = "name", nullable = false)
    private String name;
    // 画像分类描述
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

    public PortraitCategory setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public PortraitCategory setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PortraitCategory setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public PortraitCategory setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PortraitCategory setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public PortraitCategory setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PortraitCategory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PortraitCategory setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
