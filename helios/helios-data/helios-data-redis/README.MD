# 项目介绍
公共的工具Redis Cache类，支持对每个cache单独配置。
1. ttl 过期时间，单位ms
2. sleepTime 锁表时间，单位ms 默认 50ms
3. cacheNullValues 是否对空值检测
4. keySerializer 键的序列化（不支持）
5. valueSerializer 值的序列化（不支持）
6. conversionService key（不支持）
    
# 使用

#### 配置 application.properties
```$xslt
# cache
spring.cache.type=REDIS

# redis
# cache
spring.cache.type=REDIS

# redis
spring.redis.host=${redis.host}
spring.redis.port=${redis.port}
spring.redis.database=${redis.database}
spring.redis.password=${redis.password}
spring.data.redis.repositories.enabled=false

## 独立设置cache的配置
redis.cache.properties[greek-ifaces-tokens].ttl=${greek-ifaces-tokens.ttl}
redis.cache.properties[greek-ifaces-tokens].sleepTime=0
redis.cache.properties[greek-ifaces-tokens].cacheNullValues=false

# 针对cache的field配置ttl
redis.cache.properties[greek-ifaces-tokens].fieldTtl[XLportrait]=${greek-ifaces-tokens.XLportrait.ttl}```

#### 代码配置
1. 使用缓存
    代码使用可参考 test 中 RedisCacheService类 和 Application类。
