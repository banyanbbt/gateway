package org.banyan.gateway.helios.data.redis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCacheProperties
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月07日 14:20:00
 */
@ConfigurationProperties(prefix = "redis.cache")
public class RedisCacheProperties {
    private Map<String, RedisCacheConfig> properties = new HashMap<>();

    public Map<String, RedisCacheConfig> getProperties() {
        return properties;
    }

    public RedisCacheProperties setProperties(Map<String, RedisCacheConfig> properties) {
        this.properties = properties;
        return this;
    }
}
