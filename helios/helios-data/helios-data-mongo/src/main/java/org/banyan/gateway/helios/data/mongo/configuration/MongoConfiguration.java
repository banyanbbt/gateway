package org.banyan.gateway.helios.data.mongo.configuration;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MongoConfiguration
 * 修改mongo配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月21日 16:24:00
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.client")
public class MongoConfiguration {
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory mongoDbFactory, MongoMappingContext mongoMappingContext, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ex) {
            // Ignore
        }
        return mappingConverter;
    }
}
