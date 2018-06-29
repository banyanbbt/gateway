package org.banyan.gateway.pan;

import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.ares.IAresService;
import org.banyan.gateway.helios.proto.iface.thirdparty.pan.IPanService;
import org.banyan.gateway.helios.util.codec.RSAUtil;
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
@EnableConfigurationProperties(PanProperties.class)
@ConditionalOnClass(PanService.class)
public class PanAutoConfiguration {

    @Autowired
    private PanProperties panProperties;

    @Bean
    public ThirdpartyRecordService thirdpartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(PanService.class)
    public IPanService iPanService(ThirdpartyRecordService thirdPartyRecordService) {
        return new PanService(thirdPartyRecordService, this.panProperties, new RSAUtil());
    }
}
