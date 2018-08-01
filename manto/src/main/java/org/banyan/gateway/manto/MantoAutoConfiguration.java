package org.banyan.gateway.manto;

import org.banyan.gateway.helios.data.rabbitmq.service.ThirdpartyRecordService;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.IMantoService;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 自动配置
 * @date 2018-06-27 18:30:20
 */
@Configuration
@EnableConfigurationProperties(MantoProperties.class)
@ConditionalOnClass(MantoService.class)
public class MantoAutoConfiguration {

    @Autowired
    MantoProperties properties;

    @Bean
    public ThirdpartyRecordService thirdPartyRecordService(AmqpTemplate amqpTemplate) {
        return new ThirdpartyRecordService(amqpTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(MantoService.class)
    public IMantoService mantoService(ThirdpartyRecordService thirdpartyRecordService) {
        RSAUtil rsaUtil = new RSAUtil();
        PublicKey publicKey = rsaUtil.restorePublicKey(properties.getPublicKey());
        PrivateKey privateKey = rsaUtil.restorePrivateKey(properties.getPrivateKey());
        return new MantoService(properties.getAccount(), properties.getBaseUrl(), rsaUtil, publicKey, privateKey, thirdpartyRecordService);
    }
}
