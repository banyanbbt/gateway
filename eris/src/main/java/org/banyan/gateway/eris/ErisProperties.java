package org.banyan.gateway.eris;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * Eris 配置类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/28 12:07
 */
@ConfigurationProperties(prefix = "spring.channel.eris")
public class ErisProperties {

    /**
     * 基础url
     */
    private String baseUrl;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
