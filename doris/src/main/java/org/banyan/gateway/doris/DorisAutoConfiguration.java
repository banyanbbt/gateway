package org.banyan.gateway.doris;

import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.doris.IDorisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc dubbo auto configuration
 * @date 2016-12-15 15:08:57
 */
@Configuration
@EnableConfigurationProperties(DorisProperties.class)
@ConditionalOnClass(DorisService.class)
public class DorisAutoConfiguration {

    @Autowired
    private DorisProperties dorisProperties;

    @Bean
    public ThirdpartyRecordService thirdpartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(DorisService.class)
    public IDorisService iDorisService(ThirdpartyRecordService thirdPartyRecordService) {
        return new DorisService(thirdPartyRecordService, this.dorisProperties);
    }
}
