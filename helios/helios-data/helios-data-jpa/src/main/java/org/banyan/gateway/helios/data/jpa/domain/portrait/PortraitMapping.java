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
 * @desc 各个渠道(第三方)的画像mapping
 * @date 2017-09-08 09:14:23
 */
@Entity
@Table(name = "portrait_mapping")
public class PortraitMapping implements Serializable {
    private static final long serialVersionUID = -4487834450833199723L;
    // 画像映射标记
    @Id
    @Column(name = "portrait", nullable = false)
    private String portrait;
    // 数据源的接口
    @Column(name = "interface", nullable = false)
    private String iface;
    // 第三方/渠道的编号
    @Column(name = "symbol", nullable = false)
    private String symbol;
    // 数据源的指标分类
    @Column(name = "category", nullable = false)
    private String category;
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

    public PortraitMapping setPortrait(String portrait) {
        this.portrait = portrait;
        return this;
    }

    public String getIface() {
        return iface;
    }

    public PortraitMapping setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public PortraitMapping setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public PortraitMapping setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PortraitMapping setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public PortraitMapping setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public PortraitMapping setCategory(String category) {
        this.category = category;
        return this;
    }
}
