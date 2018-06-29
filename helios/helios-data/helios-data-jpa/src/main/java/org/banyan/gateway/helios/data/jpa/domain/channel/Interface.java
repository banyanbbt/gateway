package org.banyan.gateway.helios.data.jpa.domain.channel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 渠道第三方提供的接口
 * @date 2018-01-10 10:50:28
 */
@Entity
@Table(name = "interface")
public class Interface implements Serializable {
    private static final long serialVersionUID = -6695825045651480150L;
    // 接口编号
    @Id
    @Column(name = "interface", nullable = false)
    private String iface;
    // 接口名
    @Column(name = "name", nullable = false)
    private String name;
    // 渠道id
    @Column(name = "channel", nullable = false)
    private String channel;
    // 接口请求url, 去除baseUrl的api
    @Column(name = "api_url", nullable = false)
    private String apiUrl;
    // 接口成本价
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    // 最高价
    @Column(name = "highest_price")
    private BigDecimal highestPrice;
    // 最低价
    @Column(name = "lowest_price")
    private BigDecimal lowestPrice;
    // 接口是否可用, 0表示不可用，1表示可用
    @Column(name = "status", nullable = false)
    private boolean status;
    // 接口超时时间，单位：秒，默认120秒
    @Column(name = "timeout")
    private Integer timeout = 120;
    // 接口描述
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

    public String getIface() {
        return iface;
    }

    public Interface setIface(String iface) {
        this.iface = iface;
        return this;
    }

    public String getName() {
        return name;
    }

    public Interface setName(String name) {
        this.name = name;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public Interface setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Interface setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Interface setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public Interface setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
        return this;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public Interface setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public Interface setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public Interface setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Interface setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Interface setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Interface setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Interface setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
