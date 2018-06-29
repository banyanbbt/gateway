package org.banyan.gateway.helios.data.jpa.domain.account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * 密钥表
 *
 * @author Kevin Huang
 * @since 0.0.2
 * 2018年03月01日 10:06:00
 */
@Entity
@Table(name = "encryption")
public class Encryption implements Serializable {
    private static final long serialVersionUID = 9008303496417522388L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;
    // 名称
    @Column(name = "name", nullable = false)
    private String name;
    // banyan 私钥
    @Column(name = "private_key", nullable = false, columnDefinition = "TEXT")
    private String privateKey;
    // banyan 公钥
    @Column(name = "public_key", nullable = false, columnDefinition = "TEXT")
    private String publicKey;
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

    public Encryption setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Encryption setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public Encryption setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public Encryption setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Encryption setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Encryption setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Encryption setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
