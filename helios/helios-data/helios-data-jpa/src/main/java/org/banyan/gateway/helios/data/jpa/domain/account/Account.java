package org.banyan.gateway.helios.data.jpa.domain.account;

import org.banyan.gateway.helios.data.jpa.converter.HeliosDataJpaConverters;
import org.banyan.gateway.helios.data.jpa.model.AccountGrade;
import org.banyan.gateway.helios.data.jpa.model.AccountStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @author Kevin Huang
 *
 * @version 0.0.1
 * @desc account表
 * @date 2017-12-27 09:31:48
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = -2918191598312994108L;
    @Id
    // 账号
    @Column(name = "account", nullable = false, unique = true)
    private String account;
    // 企业id
    @Column(name = "company_id", nullable = false)
    private Integer companyId;
    // 账户状态
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Convert(converter = HeliosDataJpaConverters.AccountStatusConverter.class)
    private AccountStatus status;
    // 账户等级
    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    @Convert(converter = HeliosDataJpaConverters.AccountGradeConverter.class)
    private AccountGrade grade;
    // 账户余额
    @Column(name = "balance")
    private BigDecimal balance;
    // 联系人
    @Column(name = "contact")
    private String contact;
    // 联系email
    @Column(name = "email")
    private String email;
    // 联系人联系方式
    @Column(name = "phone")
    private String phone;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getAccount() {
        return account;
    }

    public Account setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Account setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Account setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public AccountGrade getGrade() {
        return grade;
    }

    public Account setGrade(AccountGrade grade) {
        this.grade = grade;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Account setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Account setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Account setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Account setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Account setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
