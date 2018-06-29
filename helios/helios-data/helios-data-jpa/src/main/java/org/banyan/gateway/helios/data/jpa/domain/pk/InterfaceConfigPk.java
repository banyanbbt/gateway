package org.banyan.gateway.helios.data.jpa.domain.pk;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceConfig Pk
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月19日 15:35:00
 */
public class InterfaceConfigPk implements Serializable {
    private static final long serialVersionUID = 2155968140830374394L;
    // 接口
    @Column(name = "interface", nullable = false)
    private String iface;
    // 配置键
    @Column(name = "config", nullable = false)
    private String config;

    public String getIface() {
        return iface;
    }

    public InterfaceConfigPk setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getConfig() {
        return config;
    }

    public InterfaceConfigPk setConfig(String config) {
        this.config = config;
        return this;
    }
}
