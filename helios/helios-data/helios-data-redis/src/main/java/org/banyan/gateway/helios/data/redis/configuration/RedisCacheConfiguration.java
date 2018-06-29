package org.banyan.gateway.helios.data.redis.configuration;

import org.banyan.gateway.helios.data.redis.cache.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCacheConfiguration
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月06日 20:13:00
 */
@Configuration
@EnableConfigurationProperties({CacheProperties.class, RedisCacheProperties.class})
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnMissingBean(CacheManager.class)
public class RedisCacheConfiguration {
    @Autowired
    private CacheProperties cacheProperties;
    @Autowired
    private RedisCacheProperties redisCacheProperties;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisConnectionFactory)
                .setInitialCacheConfigs(redisCacheProperties.getProperties())
                .setCaches(this.cacheProperties.getCacheNames());
        return cacheManager;
    }
}
