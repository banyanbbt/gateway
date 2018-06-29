package org.banyan.gateway.ares;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 聚合数据配置项
 * @date 2016-12-15 15:09:15
 */
@ConfigurationProperties(prefix = "spring.channel.ares")
public class AresProperties {

    // 聚合数据api的基础url
    private String baseUrl;
    // 聚合数据api ak
    private String key;

    public String getBaseUrl() {
        return baseUrl;
    }

    public AresProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getKey() {
        return key;
    }

    public AresProperties setKey(String key) {
        this.key = key;
        return this;
    }
}
