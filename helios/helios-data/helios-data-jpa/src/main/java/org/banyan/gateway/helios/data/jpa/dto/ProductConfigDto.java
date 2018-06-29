package org.banyan.gateway.helios.data.jpa.dto;

import org.apache.commons.lang.StringUtils;
import org.banyan.gateway.helios.data.jpa.domain.account.AccountConfig;
import org.banyan.gateway.helios.data.jpa.domain.product.ProductConfig;
import org.banyan.gateway.helios.data.jpa.model.ConfigPolicy;

import java.io.Serializable;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductConfigDto
 * product config dto
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 19:52:00
 */
public class ProductConfigDto implements Serializable {
    private static final long serialVersionUID = -3051123199680578642L;

    private String config;
    private ConfigPolicy policy;
    private String accountValue;
    private String configValue;

    public ProductConfigDto(String config) {
        this(config, null, null);
    }

    public ProductConfigDto(ProductConfig productConfig, AccountConfig accountConfig) {
        if (null != productConfig) {
            this.config = productConfig.getConfig();
            this.policy = productConfig.getPolicy();
            this.configValue = productConfig.getValue();
        }

        if (null != accountConfig) {
            if (StringUtils.isNotBlank(this.config)) {
                this.config = accountConfig.getConfig();
            }
            this.accountValue = accountConfig.getValue();
        }
    }

    public ProductConfigDto(String config, String accountValue, String configValue) {
        this.config = config;
        this.accountValue = accountValue;
        this.configValue = configValue;
    }

    public String getConfig() {
        return config;
    }

    public ProductConfigDto setConfig(String config) {
        this.config = config;
        return this;
    }

    public ConfigPolicy getPolicy() {
        return policy;
    }

    public ProductConfigDto setPolicy(ConfigPolicy policy) {
        this.policy = policy;
        return this;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public ProductConfigDto setAccountValue(String accountValue) {
        this.accountValue = accountValue;
        return this;
    }

    public String getConfigValue() {
        return configValue;
    }

    public ProductConfigDto setConfigValue(String configValue) {
        this.configValue = configValue;
        return this;
    }
}
