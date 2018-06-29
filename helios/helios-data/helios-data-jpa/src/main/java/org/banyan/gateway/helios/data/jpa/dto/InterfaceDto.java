package org.banyan.gateway.helios.data.jpa.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceDto
 * 渠道第三方提供的接口
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月19日 16:00:00
 */
public class InterfaceDto implements Serializable {
    private static final long serialVersionUID = -1546479650194426142L;

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
    // 创建者
    private Integer createdBy;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;
    // 接口配置
    private Map<String, String> interfaceConfigs;

    public String getIface() {
        return iface;
    }

    public InterfaceDto setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getName() {
        return name;
    }

    public InterfaceDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public InterfaceDto setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public InterfaceDto setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public InterfaceDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public InterfaceDto setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
        return this;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public InterfaceDto setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public InterfaceDto setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public InterfaceDto setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InterfaceDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public InterfaceDto setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public InterfaceDto setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public InterfaceDto setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Map<String, String> getInterfaceConfigs() {
        return interfaceConfigs;
    }

    public InterfaceDto setInterfaceConfigs(Map<String, String> interfaceConfigs) {
        this.interfaceConfigs = interfaceConfigs;
        return this;
    }
}
