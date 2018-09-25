package org.banyan.gateway.proteus;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 配置文件2
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 15:50
 */
@ConfigurationProperties(prefix = "spring.channel.proteusWD")
public class ProteusWDProperties {
    /**
     * 基础url
     */
    private String baseUrl;

    /**
     * 商户编号
     */
    private String memberId;

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 私钥字符串
     */
    private String privateKey;

    public String getBaseUrl() {
        return baseUrl;
    }

    public ProteusWDProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public ProteusWDProperties setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public ProteusWDProperties setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public ProteusWDProperties setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}
