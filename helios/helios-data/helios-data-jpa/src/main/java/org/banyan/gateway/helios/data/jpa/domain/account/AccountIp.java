package org.banyan.gateway.helios.data.jpa.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户ip白名单表
 * @date 2018-01-03 10:33:18
 */
@Entity
@Table(name = "account_ip")
public class AccountIp implements Serializable {
    private static final long serialVersionUID = 7149129717937760736L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "account", nullable = false)
    private String account;
    // 名称
    @Column(name = "name", nullable = false)
    private String name;
    // 具体ip或正则
    @Column(name = "regex", nullable = false)
    private String regex;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public AccountIp setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AccountIp setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountIp setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public AccountIp setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public AccountIp setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AccountIp setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AccountIp setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
