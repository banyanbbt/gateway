package org.banyan.gateway.helios.data.jpa.domain.portrait;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 画像表
 * @date 2018-01-10 15:21:36
 */
@Entity
@Table(name = "portrait")
public class Portrait implements Serializable {
    private static final long serialVersionUID = -849130738765886054L;
    // 画像标记
    @Id
    @Column(name = "portrait", nullable = false)
    private String portrait;
    // 分类
    @Column(name = "category_id")
    private Integer categoryId;
    // 画像名
    @Column(name = "name", nullable = false)
    private String name;
    // 画像描述
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

    public String getPortrait() {
        return portrait;
    }

    public Portrait setPortrait(String portrait) {
        this.portrait = portrait;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Portrait setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Portrait setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Portrait setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Portrait setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Portrait setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Portrait setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
