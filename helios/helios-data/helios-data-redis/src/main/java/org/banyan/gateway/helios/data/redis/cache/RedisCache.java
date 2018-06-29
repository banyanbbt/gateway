package org.banyan.gateway.helios.data.redis.cache;

import org.banyan.gateway.helios.data.redis.configuration.RedisCacheConfig;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RedisCache
 *
 * @author Kevin Huang
 * @since version
 * 2018年06月07日 10:23:00
 */
public class RedisCache extends AbstractValueAdaptingCache {
    private static final byte[] BINARY_NULL_VALUE = new JdkSerializationRedisSerializer().serialize(NullValue.INSTANCE);

    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfig redisCacheConfig;
    private final ConversionService conversionService;

    /**
     * Create new {@link org.springframework.data.redis.cache.RedisCache}.
     *
     * @param name must not be {@literal null}.
     * @param cacheWriter must not be {@literal null}.
     * @param redisCacheConfig must not be {@literal null}.
     */
    RedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfig redisCacheConfig) {
        super(redisCacheConfig.isCacheNullValues());
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(cacheWriter, "CacheWriter must not be null!");
        Assert.notNull(redisCacheConfig, "CacheConfig must not be null!");

        this.name = name;
        this.cacheWriter = cacheWriter;
        this.redisCacheConfig = redisCacheConfig;
        this.conversionService = redisCacheConfig.getConversionService();
    }

    @Override
    protected Object lookup(Object key) {
        byte[] value = cacheWriter.get(serializeCacheKey(key));
        if (value == null) {
            return null;
        }

        return deserializeCacheValue(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RedisCacheWriter getNativeCache() {
        return this.cacheWriter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper result = get(key);

        if (result != null) {
            return (T) result.get();
        }

        T value = valueFromLoader(key, valueLoader);
        put(key, value);
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && cacheValue == null) {
            return;
//            throw new IllegalArgumentException(String.format("Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.", name));
        }

        String field = convertKey(key);
        cacheWriter.put(serializeCacheKey(field), serializeCacheValue(field, cacheValue));
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && cacheValue == null) {
            return get(key);
        }

        String field = convertKey(key);
        byte[] result = cacheWriter.putIfAbsent(serializeCacheKey(field), serializeCacheValue(field, cacheValue));

        if (result == null) {
            return null;
        }

        return new SimpleValueWrapper(fromStoreValue(deserializeCacheValue(result)));
    }

    @Override
    public void evict(Object key) {
        cacheWriter.remove(serializeCacheKey(key));
    }

    @Override
    public void clear() {
        cacheWriter.clean();
    }

    protected Object preProcessCacheValue(Object value) {
        if (value != null) {
            return value;
        }

        return isAllowNullValues() ? NullValue.INSTANCE : null;
    }

    /**
     * Serialize the value to cache.
     *
     * @param key must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @return never {@literal null}.
     */
    protected byte[] serializeCacheValue(String key, Object value) {
        byte[] bytes = null;
        if (isAllowNullValues() && value instanceof NullValue) {
            bytes = BINARY_NULL_VALUE;
        } else {
            bytes = redisCacheConfig.getValueSerializer().serialize(value);
        }

        return RedisCacheUtil.convertToMsg(this.getTtl(key), bytes);
    }

    protected Object deserializeCacheValue(byte[] value) {
        byte[] msg = RedisCacheUtil.convertToBytes(value);
        if (isAllowNullValues() && ObjectUtils.nullSafeEquals(msg, BINARY_NULL_VALUE)) {
            return NullValue.INSTANCE;
        }

        return redisCacheConfig.getValueSerializer().deserialize(msg);
    }

    private String convertKey(Object key) {
        TypeDescriptor source = TypeDescriptor.forObject(key);
        if (conversionService.canConvert(source, TypeDescriptor.valueOf(String.class))) {
            return conversionService.convert(key, String.class);
        }

        Method toString = ReflectionUtils.findMethod(key.getClass(), "toString");
        if (toString != null && !Object.class.equals(toString.getDeclaringClass())) {
            return key.toString();
        }

        throw new IllegalStateException(String.format("Cannot convert %s to String. Register a Converter or override toString().", source));
    }

    private byte[] serializeCacheKey(Object key) {
        String field = key instanceof String ? (String) key : convertKey(key);
        return redisCacheConfig.getKeySerializer().serialize(field);
    }

    private static <T> T valueFromLoader(Object key, Callable<T> valueLoader) {
        try {
            return valueLoader.call();
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
    }

    public void setTtl(Object key, Long ttl) {
        String field = convertKey(key);
        this.redisCacheConfig.getFieldTtl().put(field, ttl);
    }

    private Long getTtl(String field) {
        Long ttl = this.redisCacheConfig.getFieldTtl().get(field);
        if (null == ttl) {
            ttl = this.redisCacheConfig.getTtl();
        }
        return ttl;
    }
}
