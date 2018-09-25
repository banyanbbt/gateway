package org.banyan.gateway.proteus;

import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.proteus.IProteusService;
import org.banyan.gateway.helios.util.codec.RSAUtil;
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
 * 自动配置类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 14:15
 */
@Configuration
@EnableConfigurationProperties({ProteusHYSProperties.class, ProteusWDProperties.class})
@ConditionalOnClass(ProteusService.class)
public class ProteusAutoConfiguration {

    @Autowired
    private ProteusWDProperties proteusWDProperties;

    @Bean
    public ThirdpartyRecordService thirdPartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(ProteusService.class)
    public IProteusService proteusServer(ThirdpartyRecordService thirdPartyRecordService) {
        return new ProteusService(thirdPartyRecordService, proteusWDProperties, new RSAUtil(1024));
    }
}
