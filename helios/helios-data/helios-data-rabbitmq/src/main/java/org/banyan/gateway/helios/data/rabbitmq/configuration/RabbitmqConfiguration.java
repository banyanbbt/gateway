package org.banyan.gateway.helios.data.rabbitmq.configuration;

import com.rabbitmq.client.Channel;
import org.banyan.gateway.helios.data.rabbitmq.service.ProductRecordService;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.banyan.gateway.helios.data.rabbitmq.constant.Constant.*;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * RabbitmqConfiguration
 * rabbit mq
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月19日 19:46:00
 */
@Configuration
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitmqConfiguration {
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Configuration
    @ConditionalOnBean(ThirdpartyRecordService.class)
    protected static class ThirdpartyRabbitmqConfiguration {
        @Bean(name = "thirdpartyQueue")
        public Queue thirdpartyQueue() {
            return new Queue(QUEUE_THIRD_PARTY, true, false, false);
        }

        @Bean(name = "thirdpartyExchange")
        public DirectExchange thirdpartyExchange() {
            return new DirectExchange(EXCHANGE_THIRD_PARTY, true, false);
        }

        @Bean
        public Binding bindingThirdpartyQueue(Queue thirdpartyQueue, DirectExchange thirdpartyExchange) {
            return BindingBuilder.bind(thirdpartyQueue).to(thirdpartyExchange).with(thirdpartyQueue.getName());
        }
    }

    @Configuration
    @ConditionalOnBean(ProductRecordService.class)
    protected static class ProductRabbitmqConfiguration {

        @Bean(name = "productRecordQueue")
        public Queue productRecordQueue() {
            return new Queue(QUEUE_PRODUCT_RECORD, true, false, false);
        }

        @Bean(name = "productRecordExchange")
        public FanoutExchange productRecordExchange() {
            return new FanoutExchange(EXCHANGE_PRODUCT_RECORD, true, false);
        }

        @Bean
        public Binding bindingProductRecordQueue(Queue productRecordQueue, FanoutExchange productRecordExchange) {
            return BindingBuilder.bind(productRecordQueue).to(productRecordExchange);
        }
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(RabbitProperties config) throws Exception {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        if (config.determineHost() != null) {
            factory.setHost(config.determineHost());
        }
        factory.setPort(config.determinePort());
        if (config.determineUsername() != null) {
            factory.setUsername(config.determineUsername());
        }
        if (config.determinePassword() != null) {
            factory.setPassword(config.determinePassword());
        }
        if (config.determineVirtualHost() != null) {
            factory.setVirtualHost(config.determineVirtualHost());
        }
        if (config.getRequestedHeartbeat() != null) {
            factory.setRequestedHeartbeat(config.getRequestedHeartbeat());
        }
        RabbitProperties.Ssl ssl = config.getSsl();
        if (ssl.isEnabled()) {
            factory.setUseSSL(true);
            if (ssl.getAlgorithm() != null) {
                factory.setSslAlgorithm(ssl.getAlgorithm());
            }
            factory.setKeyStore(ssl.getKeyStore());
            factory.setKeyStorePassphrase(ssl.getKeyStorePassword());
            factory.setTrustStore(ssl.getTrustStore());
            factory.setTrustStorePassphrase(ssl.getTrustStorePassword());
        }
        if (config.getConnectionTimeout() != null) {
            factory.setConnectionTimeout(config.getConnectionTimeout());
        }

        // 添加recovery两个配置
        factory.setAutomaticRecoveryEnabled(true);
        factory.setTopologyRecoveryEnabled(true);

        factory.afterPropertiesSet();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory.getObject());
        connectionFactory.setAddresses(config.determineAddresses());
        connectionFactory.setPublisherConfirms(config.isPublisherConfirms());
        connectionFactory.setPublisherReturns(config.isPublisherReturns());
        if (config.getCache().getChannel().getSize() != null) {
            connectionFactory.setChannelCacheSize(config.getCache().getChannel().getSize());
        }
        if (config.getCache().getConnection().getMode() != null) {
            connectionFactory.setCacheMode(config.getCache().getConnection().getMode());
        }
        if (config.getCache().getConnection().getSize() != null) {
            connectionFactory.setConnectionCacheSize(config.getCache().getConnection().getSize());
        }
        if (config.getCache().getChannel().getCheckoutTimeout() != null) {
            connectionFactory.setChannelCheckoutTimeout(config.getCache().getChannel().getCheckoutTimeout());
        }
        return connectionFactory;
    }
}
