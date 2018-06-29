package org.banyan.gateway.helios.data.jpa.dto;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountKey;
import org.banyan.gateway.helios.data.jpa.domain.account.Encryption;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * AccountKeyDto
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月02日 14:17:00
 */
public class AccountKeyDto implements Serializable {
    private static final long serialVersionUID = 1240509837828587011L;

    private Integer id;
    private String account;
    // banyan密钥id
    private Integer encryptionId;
    // banyan密钥
    private EncryptionDto encryptionDto;
    // 客户私钥
    private String privateKey;
    // 客户公钥
    private String publicKey;
    // 是否可用
    private boolean available;
    // 生效时间
    private Date effectiveAt;
    // 过期时间
    private Date expireAt;

    public AccountKeyDto(AccountKey accountKey) {
        this.id = accountKey.getId();
        this.account = accountKey.getAccount();
        this.encryptionId = accountKey.getEncryptionId();
        this.privateKey = accountKey.getPrivateKey();
        this.publicKey = accountKey.getPublicKey();
        this.available = accountKey.isAvailable();
        this.effectiveAt = accountKey.getEffectiveAt();
        this.expireAt = accountKey.getExpireAt();
    }

    public AccountKeyDto(AccountKey accountKey, Encryption encryption) {
        this(accountKey);
        this.encryptionDto = new EncryptionDto(encryption);
    }

    public Integer getId() {
        return id;
    }

    public AccountKeyDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AccountKeyDto setAccount(String account) {
        this.account = account;
        return this;
    }

    public Integer getEncryptionId() {
        return encryptionId;
    }

    public AccountKeyDto setEncryptionId(Integer encryptionId) {
        this.encryptionId = encryptionId;
        return this;
    }

    public EncryptionDto getEncryptionDto() {
        return encryptionDto;
    }

    public AccountKeyDto setEncryptionDto(EncryptionDto encryptionDto) {
        this.encryptionDto = encryptionDto;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public AccountKeyDto setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public AccountKeyDto setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public AccountKeyDto setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public Date getEffectiveAt() {
        return effectiveAt;
    }

    public AccountKeyDto setEffectiveAt(Date effectiveAt) {
        this.effectiveAt = effectiveAt;
        return this;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public AccountKeyDto setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
        return this;
    }

}
