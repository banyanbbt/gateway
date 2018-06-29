# 项目介绍
公共Mongo的工具类，内置义务的三个collections。根据配置生效。
1. product_record
2. third_party_record
3. valid_cache_record

# 使用

#### 配置 application.properties
```$xslt
# MONGO 信息配置
spring.data.mongodb.database=${mongodb.database}
spring.data.mongodb.repositories.enabled=false
spring.data.mongodb.uri=${mongodb.uri}

spring.data.mongodb.client.min-connections-per-host=3
spring.data.mongodb.client.required-replica-set-name=${mongodb.requiredReplicaSetName}
spring.data.mongodb.client.connect-timeout=15000
spring.data.mongodb.client.socket-keep-alive=true
spring.data.mongodb.client.socket-timeout=15000
```

#### 代码配置
1. 使用缓存
    在引用的项目启动类中添加
    ```
    @ComponentScan(basePackages={"org.banyan.gateway.helios.data.mongo.service"})
    ```
    
    ```
    ValidCacheRecord cacheRecord = mongoService.getValidCacheRecord("61112", "cid", "mobile", "name", null);
    if (null == cacheRecord) {
        cacheRecord = <<请求第三方>>;
    }
    ```
