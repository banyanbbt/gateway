# 项目介绍
公共Rabbitmq的工具类，内置义务相关的队列。根据配置生效。


# 使用

#### 配置 application.properties
```$xslt
# rabbitmq 信息配置
spring.rabbitmq.addresses=${rabbit.addresses}
spring.rabbitmq.username=${rabbit.username}
spring.rabbitmq.password=${rabbit.password}
spring.rabbitmq.virtual-host=${rabbit.vhost}
spring.rabbitmq.dynamic=true
spring.rabbitmq.requested-heartbeat=60
```

#### 代码配置
1. 使用 第三方队列 或 要素缓存 队列
    在引用的项目中添加 Configuration
    ```
    @Bean
    public ThirdpartyRecordService thirdpartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }
    ```
2. 使用系统请求记录队列
    在引用的项目中添加 Configuration
    ```
    @Bean
    public ProductRecordService productRecordService(AmqpTemplate amqpTemplate) {
        return new ProductRecordService(amqpTemplate);
    }
    ```