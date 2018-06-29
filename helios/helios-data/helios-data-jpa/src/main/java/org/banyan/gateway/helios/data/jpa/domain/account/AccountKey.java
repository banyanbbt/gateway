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
 * @desc 账户公私钥表
 * @date 2018-01-03 10:33:30
 */
@Entity
@Table(name = "account_key")
public class AccountKey implements Serializable {
    private static final long serialVersionUID = 8941225817456943783L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "account", nullable = false)
    private String account;
    // banyan密钥
    @Column(name = "encryption_id", nullable = false)
    private Integer encryptionId;
    // 客户私钥
    @Column(name = "private_key", columnDefinition = "TEXT")
    private String privateKey;
    // 客户公钥
    @Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
    private String publicKey;
    // 是否可用
    @Column(name = "available", nullable = false)
    private boolean available;
    // 生效时间
    @Column(name = "effective_at")
    private Date effectiveAt;
    // 过期时间
    @Column(name = "expire_at")
    private Date expireAt;
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

    public AccountKey setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AccountKey setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getEncryptionId() {
        return encryptionId;
    }

    public AccountKey setEncryptionId(Integer encryptionId) {
        this.encryptionId = encryptionId;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public AccountKey setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public AccountKey setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public AccountKey setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public Date getEffectiveAt() {
        return effectiveAt;
    }

    public AccountKey setEffectiveAt(Date effectiveAt) {
        this.effectiveAt = effectiveAt;
        return this;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public AccountKey setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public AccountKey setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public AccountKey setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public AccountKey setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
