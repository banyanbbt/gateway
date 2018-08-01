package org.banyan.gateway.hebe.application;

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
 * Copyright (C), 2018, Banyan Network Foundation
 * Launcher
 *
 * @author Kevin Huang
 * @since 0.1.0
 * 2018年07月31日 17:20:00
 */
@SpringBootApplication
@ComponentScan(basePackages={"org.banyan.gateway.hebe"})
@PropertySource("classpath:properties/hebe.properties")
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static volatile boolean RUNNING = true;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        Set<ApplicationListener<?>> listeners = builder.application().getListeners();
        listeners.removeIf(listener -> listener instanceof LoggingApplicationListener);
        builder.application().setListeners(listeners);
        builder.run(args);

        LOGGER.info("hebe start successfully");

        // 优雅停止项目
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("try to stop the system");
            synchronized (Application.class) {
                RUNNING = false;
                Application.class.notify();
            }
        }));

        synchronized (Application.class) {
            while (RUNNING) {
                try {
                    Application.class.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("wait error");
                    e.printStackTrace();
                }
            }
        }
    }
}
