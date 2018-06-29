package org.banyan.gateway.pan;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 运营商数据配置项
 * @date 2016-12-15 15:09:15
 */
@ConfigurationProperties(prefix = "spring.channel.pan")
public class PanProperties {

    // 运营商的基础url
    private String baseUrl;
    // 运营商的account
    private String account;
    // 运营商公钥
    private String publicKey;
    // 我方私钥
    private String privateKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public PanProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PanProperties setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public PanProperties setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public PanProperties setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}
