package org.banyan.gateway.helios.data.redis.cache;


import org.banyan.gateway.helios.data.redis.configuration.RedisCacheConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;


/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCacheManager
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月08日 16:41:00
 */
public class RedisCacheManager extends AbstractTransactionSupportingCacheManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheManager.class);

    private final RedisConnectionFactory connectionFactory;
    private final RedisCacheConfig defaultCacheConfig;
    private final Set<String> caches;
    private final Map<String, RedisCacheConfig> initialCacheConfigs;

    public RedisCacheManager(RedisConnectionFactory connectionFactory) {
        Assert.notNull(connectionFactory, "RedisConnectionFactory must not be null!");
        this.connectionFactory = connectionFactory;
        this.defaultCacheConfig = new RedisCacheConfig();
        this.caches = new HashSet<>();
        this.initialCacheConfigs = new LinkedHashMap<>();
    }

    /**
     * 查询并获取
     * @param name cache name
     * @param key cache field
     * @param function miss, then exec function
     * @return value.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name, Object key, Function<RedisCache, T> function) {
        T result = null;

        RedisCache cache = (RedisCache) this.getCache(name);
        if (null != cache) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (null != valueWrapper) {
                LOGGER.info("击中缓存。Cache {}, Key {}", name, key);
                return (T) valueWrapper.get();
            }
        }

        result = function.apply(cache);
        if (null != cache) {
            cache.put(key, result);
        }

        return result;
    }

    /**
     * 清除缓存
     */
    public synchronized void evict(String name, Object key) {
        Cache cache = this.getCache(name);
        if (null != cache) {
            LOGGER.info("删除缓存。Cache {}, Key {}", name, key);
            cache.evict(key);
        }
    }

    @Override
    protected Collection<RedisCache> loadCaches() {
        List<RedisCache> redisCaches = new LinkedList<>();
        if (!CollectionUtils.isEmpty(this.initialCacheConfigs)) {
            this.caches.addAll(this.initialCacheConfigs.keySet());
        }

        this.caches.forEach(cache -> redisCaches.add(createRedisCache(cache, initialCacheConfigs.get(cache))));
        return redisCaches;
    }

    @Override
    protected RedisCache getMissingCache(String name) {
        return createRedisCache(name, defaultCacheConfig);
    }

    private RedisCache createRedisCache(String name, RedisCacheConfig cacheConfig) {
        Long sleepTime = null;
        if (null == cacheConfig || !isAvailable(sleepTime = cacheConfig.getSleepTime())) {
            sleepTime = 50L;
        }
        RedisCacheWriter cacheWriter = new RedisCacheWriter(connectionFactory, name, sleepTime);
        return new RedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig);
    }

    public RedisCacheManager setDefaultCacheConfig(RedisCacheConfig defaultCacheConfig) {
        if (null != defaultCacheConfig) {
            this.defaultCacheConfig.copyOf(defaultCacheConfig);
        }
        return this;
    }

    public RedisCacheManager setInitialCacheConfigs(Map<String, RedisCacheConfig> initialCacheConfigs) {
        if (!CollectionUtils.isEmpty(initialCacheConfigs)) {
            this.initialCacheConfigs.putAll(initialCacheConfigs);
        }
        return this;
    }

    public RedisCacheManager setCaches(Collection<String> caches) {
        if (!CollectionUtils.isEmpty(caches)) {
            this.caches.addAll(caches);
        }
        return this;
    }

    private static boolean isAvailable(Long ttl) {
        return ttl != null && ttl > 0;
    }
}
