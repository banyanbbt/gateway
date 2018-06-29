package org.banyan.gateway.helios.data.jpa.domain.channel;

import org.banyan.gateway.helios.data.jpa.domain.pk.InterfaceConfigPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceConfig
 * 渠道第三方提供的接口配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月19日 15:24:00
 */
@Entity
@Table(name = "interface_config")
@IdClass(InterfaceConfigPk.class)
public class InterfaceConfig implements Serializable {
    private static final long serialVersionUID = 7933782372951159462L;
    // 接口
    @Id
    @Column(name = "interface", nullable = false)
    private String iface;
    // 配置键
    @Id
    @Column(name = "config", nullable = false)
    private String config;
    // 配置值
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getIface() {
        return iface;
    }

    public InterfaceConfig setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getConfig() {
        return config;
    }

    public InterfaceConfig setConfig(String config) {
        this.config = config;
        return this;
    }

    public String getValue() {
        return value;
    }

    public InterfaceConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public InterfaceConfig setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public InterfaceConfig setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}