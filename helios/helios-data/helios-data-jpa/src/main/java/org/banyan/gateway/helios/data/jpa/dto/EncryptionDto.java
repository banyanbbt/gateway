package org.banyan.gateway.helios.data.jpa.dto;

import org.banyan.gateway.helios.data.jpa.domain.account.Encryption;

import java.io.Serializable;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Encryption Dto
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月02日 14:28:00
 */
public class EncryptionDto implements Serializable {
    private static final long serialVersionUID = 845702044293080583L;

    private Integer id;
    private String name;
    private String privateKey;
    private String publicKey;

    public EncryptionDto(Encryption encryption) {
        this.id = encryption.getId();
        this.name = encryption.getName();
        this.privateKey = encryption.getPrivateKey();
        this.publicKey = encryption.getPublicKey();
    }

    public Integer getId() {
        return id;
    }

    public EncryptionDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EncryptionDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public EncryptionDto setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public EncryptionDto setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }
}
