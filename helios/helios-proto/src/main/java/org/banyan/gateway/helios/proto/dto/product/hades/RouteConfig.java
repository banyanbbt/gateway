package org.banyan.gateway.helios.proto.dto.product.hades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RouteConfig
 * 数据源路由配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月20日 14:09:00
 */
public class RouteConfig implements Serializable {
    private static final long serialVersionUID = -8420714924116737751L;

    // 接口编号
    private String iface;
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
    private Map<String, String> interfaceConfigs;

    public String getIface() {
        return iface;
    }

    public RouteConfig setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getName() {
        return name;
    }

    public RouteConfig setName(String name) {
        this.name = name;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public RouteConfig setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public RouteConfig setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RouteConfig setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public RouteConfig setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
        return this;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public RouteConfig setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public RouteConfig setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public RouteConfig setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RouteConfig setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, String> getInterfaceConfigs() {
        return interfaceConfigs;
    }

    public RouteConfig setInterfaceConfigs(Map<String, String> interfaceConfigs) {
        this.interfaceConfigs = interfaceConfigs;
        return this;
    }
}
