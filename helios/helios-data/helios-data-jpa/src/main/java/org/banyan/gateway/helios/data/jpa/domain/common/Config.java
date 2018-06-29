package org.banyan.gateway.helios.data.jpa.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Config
 * 配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月13日 17:47:00
 */
@Entity
@Table(name = "config")
public class Config implements Serializable {
    private static final long serialVersionUID = -3396689678721767430L;
    // 配置键
    @Id
    @Column(name = "config", nullable = false)
    private String config;
    // 描述
    @Column(name = "description", nullable = false)
    private String description;
    // 配置值
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getConfig() {
        return config;
    }

    public Config setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Config setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Config setValue(String value) {
        this.value = value;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Config setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Config setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
