package org.banyan.gateway.eris;

import org.banyan.gateway.eris.util.RequestUtil;
import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.eris.IErisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * Eris 配置类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/28 09:12
 */
@Configuration
@EnableConfigurationProperties(ErisProperties.class)
@ConditionalOnClass(ErisService.class)
public class ErisAutoConfiguration {

    @Autowired
    private ErisProperties erisProperties;

    @Bean
    public ThirdpartyRecordService thirdPartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(ErisService.class)
    public IErisService erisService(ThirdpartyRecordService thirdpartyRecordService) {
        return new ErisService(erisProperties, new RequestUtil(), thirdpartyRecordService);
    }
}
