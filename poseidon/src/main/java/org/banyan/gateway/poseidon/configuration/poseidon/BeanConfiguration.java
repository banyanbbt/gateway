package org.banyan.gateway.poseidon.configuration.poseidon;

import org.banyan.gateway.helios.data.mongo.service.MongoService;
import org.banyan.gateway.helios.data.rabbitmq.service.ProductRecordService;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.poseidon.listener.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * BeanConfiguration
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月22日 17:02:00
 */
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class BeanConfiguration implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanConfiguration.class);
    private ApplicationContext applicationContext;
    @Autowired
    private Queue productRecordQueue;
    @Autowired
    private Queue frontendProductRecordQueue;
    @Autowired
    private MongoService mongoService;
    @Autowired
    private Queue validCacheQueue;
    @Autowired
    private Queue thirdpartyQueue;
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private FrontendProductRecordHandler frontendProductRecordHandler;
    @Autowired
    private ProductRecordHandler productRecordHandler;
    @Autowired
    private ThirdpartyRecordHandler thirdpartyRecordHandler;
    @Autowired
    private ValidCacheHandler validCacheHandler;

    @Bean
    public ThreadPoolExecutor executor() {
        ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(20, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        taskExecutor.prestartAllCoreThreads();
        return taskExecutor;
    }

    @Bean
    public ProductRecordService productRecordService(AmqpTemplate amqpTemplate) {
        return new ProductRecordService(amqpTemplate);
    }

    @Bean
    public ThirdpartyRecordService thirdpartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean("frontendProductRecordMessageListenerContainer")
    public SimpleMessageListenerContainer frontendProductRecordMessageListenerContainer(RabbitProperties rabbitProperties, MessageConverter messageConverter, ThreadPoolExecutor executor) {
        SimpleMessageListenerContainer container = createMessageListenerContainer(rabbitProperties, messageConverter, executor);
        container.setQueues(frontendProductRecordQueue);
        container.setMessageListener(new RecordMessageListener(messageConverter, mongoService, frontendProductRecordHandler));
        return container;
    }

    @Bean("productRecordMessageListenerContainer")
    public SimpleMessageListenerContainer productRecordMessageListenerContainer(RabbitProperties rabbitProperties, MessageConverter messageConverter, ThreadPoolExecutor executor) {
        SimpleMessageListenerContainer container = createMessageListenerContainer(rabbitProperties, messageConverter, executor);
        container.setQueues(productRecordQueue);
        container.setMessageListener(new RecordMessageListener(messageConverter, mongoService, productRecordHandler));
        return container;
    }

    @Bean("thirdPartyRecordMessageListenerContainer")
    public SimpleMessageListenerContainer thirdPartyRecordMessageListenerContainer(RabbitProperties rabbitProperties, MessageConverter messageConverter, ThreadPoolExecutor executor) {
        SimpleMessageListenerContainer container = createMessageListenerContainer(rabbitProperties, messageConverter, executor);
        container.setQueues(thirdpartyQueue);
        container.setMessageListener(new RecordMessageListener(messageConverter, mongoService, thirdpartyRecordHandler));
        return container;
    }

    @Bean("validCacheRecordMessageListenerContainer")
    public SimpleMessageListenerContainer validCacheRecordMessageListenerContainer(RabbitProperties rabbitProperties, MessageConverter messageConverter, ThreadPoolExecutor executor) {
        SimpleMessageListenerContainer container = createMessageListenerContainer(rabbitProperties, messageConverter, executor);
        container.setQueues(validCacheQueue);
        container.setMessageListener(new RecordMessageListener(messageConverter, mongoService, validCacheHandler));
        return container;
    }

    public SimpleMessageListenerContainer createMessageListenerContainer(RabbitProperties rabbitProperties, MessageConverter messageConverter, Executor executor) {
        SimpleMessageListenerContainer instance = new SimpleMessageListenerContainer(this.cachingConnectionFactory);
        instance.setErrorHandler(t -> LOGGER.error("消息处理失败", t));
        instance.setMessageConverter(messageConverter);
        instance.setChannelTransacted(true);
        instance.setExposeListenerChannel(true);
        instance.setTaskExecutor(executor);
        instance.setRabbitAdmin((RabbitAdmin) amqpAdmin);

        RabbitProperties.AmqpContainer config = rabbitProperties.getListener().getSimple();
        if (null != config.getAcknowledgeMode()) {
            instance.setAcknowledgeMode(config.getAcknowledgeMode());
        }
        instance.setAutoStartup(config.isAutoStartup());
        if (this.applicationContext != null) {
            instance.setApplicationContext(this.applicationContext);
        }
        if (null != config.getTransactionSize()) {
            instance.setTxSize(config.getTransactionSize());
        }
        if (null != config.getConcurrency()) {
            instance.setConcurrentConsumers(config.getConcurrency());
        }
        if (null != config.getMaxConcurrency()) {
            instance.setMaxConcurrentConsumers(config.getMaxConcurrency());
        }
        if (null != config.getPrefetch()) {
            instance.setPrefetchCount(config.getPrefetch());
        }
        if (null != config.getDefaultRequeueRejected()) {
            instance.setDefaultRequeueRejected(config.getDefaultRequeueRejected());
        }
        if (null != config.getIdleEventInterval()) {
            instance.setIdleEventInterval(config.getIdleEventInterval());
        }
        RabbitProperties.ListenerRetry retryConfig = config.getRetry();
        if (retryConfig.isEnabled()) {
            RetryInterceptorBuilder<?> builder = (retryConfig.isStateless() ? RetryInterceptorBuilder.stateless() : RetryInterceptorBuilder.stateful());
            builder.maxAttempts(retryConfig.getMaxAttempts());
            builder.backOffOptions(retryConfig.getInitialInterval(), retryConfig.getMultiplier(), retryConfig.getMaxInterval());

            builder.recoverer(new RejectAndDontRequeueRecoverer());
            instance.setAdviceChain(builder.build());
        }
        instance.setRecoveryBackOff(new ExponentialBackOff());
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
