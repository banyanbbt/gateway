package org.banyan.gateway.zues.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 项目启动器
 * @date 2018-01-11 17:11:17
 */
@SpringBootApplication
@ComponentScan(basePackages={"org.banyan.gateway.zues", "org.banyan.gateway.helios.data.jpa"})
@PropertySource("classpath:properties/zues.properties")
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        Set<ApplicationListener<?>> listeners = builder.application().getListeners();
        listeners.removeIf(listener -> listener instanceof LoggingApplicationListener);
        builder.application().setListeners(listeners);
        builder.run(args);

        LOGGER.info("zues start successfully");
    }
}