package org.banyan.gateway.helios.proto.dto.product;

import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.common.Interface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceConfig
 * 数据源配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月20日 13:55:00
 */
public class InterfaceConfig implements Serializable {
    private static final long serialVersionUID = 929348310358893950L;

    private Map<Interface, InterfaceConfig.Item> configs = new HashMap<>();

    public InterfaceConfig.Item get(Interface iface) {
        return configs.get(iface);
    }

    public InterfaceConfig.Item set(Interface iface, InterfaceConfig.Item value) {
        return configs.put(iface, value);
    }

    public Map<Interface, Item> getConfigs() {
        return configs;
    }

    public InterfaceConfig setConfigs(Map<Interface, Item> configs) {
        this.configs = configs;
        return this;
    }

    public static class Item implements Serializable {
        private static final long serialVersionUID = -2880370308673840619L;
        // 接口编号
        private Interface iface;
        // 接口名
        private String name;
        // 渠道id
        private String channel;
        // 接口请求url, 去除baseUrl的api
        private String apiUrl;
        // 接口成本价
        private BigDecimal price;
        // 最高价
        private BigDecimal highestPrice;
        // 最低价
        private BigDecimal lowestPrice;
        // 接口是否可用, 0表示不可用，1表示可用
        private boolean status;
        // 接口超时时间，单位：秒，默认120秒
        private Integer timeout;
        // 接口描述
        private String description;
        // 接口配置
        private Map<ConfigKey, String> configs;

        public Interface getIface() {
            return iface;
        }

        public Item setIface(Interface iface) {
            this.iface = iface;
            return this;
        }

        public String getName() {
            return name;
        }

        public Item setName(String name) {
            this.name = name;
            return this;
        }

        public String getChannel() {
            return channel;
        }

        public Item setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public Item setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public Item setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BigDecimal getHighestPrice() {
            return highestPrice;
        }

        public Item setHighestPrice(BigDecimal highestPrice) {
            this.highestPrice = highestPrice;
            return this;
        }

        public BigDecimal getLowestPrice() {
            return lowestPrice;
        }

        public Item setLowestPrice(BigDecimal lowestPrice) {
            this.lowestPrice = lowestPrice;
            return this;
        }

        public boolean isStatus() {
            return status;
        }

        public Item setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public Item setTimeout(Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Item setDescription(String description) {
            this.description = description;
            return this;
        }

        public Map<ConfigKey, String> getConfigs() {
            return configs;
        }

        public Item setConfigs(Map<ConfigKey, String> configs) {
            this.configs = configs;
            return this;
        }
    }
}
