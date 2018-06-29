package org.banyan.gateway.helios.data.jpa.domain.channel;

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
 * @desc 第三方数据提供渠道方表
 * @date 2018-01-03 11:14:23
 */
@Entity
@Table(name = "channel")
public class Channel implements Serializable {
    private static final long serialVersionUID = -2720223884851822320L;
    // 第三方编号
    @Id
    @Column(name = "channel", nullable = false)
    private String channel;
    // 第三方请求基础url
    @Column(name = "base_url")
    private String baseUrl;
    // 渠道是否可用, 0表示不可用，1表示可用
    @Column(name = "status", nullable = false)
    private boolean status;
    // 第三方名字
    @Column(name = "company_name", nullable = false)
    private String companyName;
    // 联系人
    @Column(name = "contact")
    private String contact;
    // 联系email
    @Column(name = "email")
    private String email;
    // 第三方联系方式
    @Column(name = "phone")
    private String phone;
    // 联系地址
    @Column(name = "address")
    private String address;
    // 第三方官网地址
    @Column(name = "company_website")
    private String companyWebsite;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getChannel() {
        return channel;
    }

    public Channel setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Channel setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public Channel setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Channel setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Channel setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Channel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Channel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Channel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public Channel setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Channel setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Channel setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Channel setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
