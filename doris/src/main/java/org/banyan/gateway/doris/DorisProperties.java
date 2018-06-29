package org.banyan.gateway.doris;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 百度配置项
 * @date 2016-12-15 15:09:15
 */
@ConfigurationProperties(prefix = "spring.channel.doris")
public class DorisProperties {

    // 百度api的基础url
    private String baseUrl;
    // 百度api ak
    private String ak;

    public String getBaseUrl() {
        return baseUrl;
    }

    public DorisProperties setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getAk() {
        return ak;
    }

    public DorisProperties setAk(String ak) {
        this.ak = ak;
        return this;
    }
}
