package org.banyan.gateway.zues.configuration.common;

import org.banyan.gateway.helios.data.rabbitmq.service.ProductRecordService;
import org.banyan.gateway.helios.proto.iface.product.hades.IRouteService;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.banyan.spring.boot.starter.dubbo.DubboService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 构造通用的bean
 * @date 2018-01-22 20:38:23
 */
@Configuration
public class BeanConfiguration {
    @Autowired
    private DubboService dubboService;

    @Bean
    public RSAUtil rsaUtil() {
        return new RSAUtil();
    }

    @Bean
    public IRouteService routeService() {
        return dubboService.get(IRouteService.class);
    }

    @Bean
    public ProductRecordService productRecordService(AmqpTemplate amqpTemplate) {
        return new ProductRecordService(amqpTemplate);
    }
}