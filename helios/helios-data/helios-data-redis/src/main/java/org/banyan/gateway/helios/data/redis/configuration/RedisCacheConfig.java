package org.banyan.gateway.helios.data.redis.configuration;

import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCacheConfig
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月07日 09:46:00
 */
public class RedisCacheConfig {
    private Long ttl;
    private Map<String, Long> fieldTtl;
    private Long sleepTime;
    private boolean cacheNullValues;

    private RedisSerializer<String> keySerializer;
    private RedisSerializer<Object> valueSerializer;
    private ConversionService conversionService;

    /**
     * Default {@link RedisCacheConfig} using the following:
     * <dl>
     * <dt>key expiration</dt>
     * <dd>eternal</dd>
     * <dt>cache null values</dt>
     * <dd>yes</dd>
     * <dt>key serializer</dt>
     * <dd>StringRedisSerializer.class</dd>
     * <dt>value serializer</dt>
     * <dd>JdkSerializationRedisSerializer.class</dd>
     * <dt>conversion service</dt>
     * <dd>{@link DefaultFormattingConversionService} with {@link #registerDefaultConverters(ConverterRegistry) default}
     * cache key converters</dd>
     * </dl>
     *
     * @return new {@link RedisCacheConfig}.
     */
    public RedisCacheConfig() {
        this(0l, true, new StringRedisSerializer(), new JdkSerializationRedisSerializer(), null);
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        registerDefaultConverters(conversionService);
        this.setConversionService(conversionService);
    }

    private RedisCacheConfig(Long ttl, Boolean cacheNullValues, RedisSerializer<String> keySerializer, RedisSerializer<Object> valueSerializer, ConversionService conversionService) {
        this.ttl = ttl;
        this.fieldTtl = new HashMap<>();
        this.cacheNullValues = cacheNullValues;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.conversionService = conversionService;
    }

    public RedisCacheConfig copyOf(RedisCacheConfig redisCacheConfig) {
        this.ttl = redisCacheConfig.getTtl();
        this.fieldTtl = redisCacheConfig.getFieldTtl();
        this.sleepTime = redisCacheConfig.getSleepTime();
        this.cacheNullValues = redisCacheConfig.isCacheNullValues();
        this.keySerializer = redisCacheConfig.getKeySerializer();
        this.valueSerializer = redisCacheConfig.getValueSerializer();
        this.conversionService = redisCacheConfig.getConversionService();
        return this;
    }

    public Long getTtl() {
        return ttl;
    }

    public RedisCacheConfig setTtl(Long ttl) {
        this.ttl = ttl;
        return this;
    }

    public Map<String, Long> getFieldTtl() {
        return fieldTtl;
    }

    public RedisCacheConfig setFieldTtl(Map<String, Long> fieldTtl) {
        this.fieldTtl = fieldTtl;
        return this;
    }

    public Long getSleepTime() {
        return sleepTime;
    }

    public RedisCacheConfig setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public RedisCacheConfig setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
        return this;
    }

    public RedisSerializer<String> getKeySerializer() {
        return keySerializer;
    }

    public RedisCacheConfig setKeySerializer(RedisSerializer<String> keySerializer) {
        this.keySerializer = keySerializer;
        return this;
    }

    public RedisSerializer<Object> getValueSerializer() {
        return valueSerializer;
    }

    public RedisCacheConfig setValueSerializer(RedisSerializer<Object> valueSerializer) {
        this.valueSerializer = valueSerializer;
        return this;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public RedisCacheConfig setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
        return this;
    }

    /**
     * Registers default cache key converters. The following converters get registered:
     * <ul>
     * <li>{@link String} to {@link byte byte[]} using UTF-8 encoding.</li>
     * <li>{@link SimpleKey} to {@link String}</li>
     *
     * @param registry must not be {@literal null}.
     */
    public static void registerDefaultConverters(ConverterRegistry registry) {

        Assert.notNull(registry, "ConverterRegistry must not be null!");

        registry.addConverter(String.class, byte[].class, source -> source.getBytes(StandardCharsets.UTF_8));
        registry.addConverter(SimpleKey.class, String.class, SimpleKey::toString);
    }
}
