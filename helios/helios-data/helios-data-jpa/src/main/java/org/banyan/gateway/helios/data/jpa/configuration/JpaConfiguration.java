package org.banyan.gateway.helios.data.jpa.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc jpa相关的配置
 * @date 2016-08-26 09:32:15
 */
@Configuration
@EnableTransactionManagement
@EntityScan("org.banyan.gateway.helios.data.jpa.domain")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = { "org.banyan.gateway.helios.data.jpa.repository" })
public class JpaConfiguration {
}
