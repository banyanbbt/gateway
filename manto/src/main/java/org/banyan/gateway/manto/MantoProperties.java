package org.banyan.gateway.manto;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 配置
 * @date 2018-06-27 18:30:20
 */
@ConfigurationProperties(prefix = "spring.channel.manto")
public class MantoProperties {
    private String baseUrl;

    private String account;

    private String privateKey;

    private String publicKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public MantoProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public MantoProperties setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public MantoProperties setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public MantoProperties setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }
}
