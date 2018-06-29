package org.banyan.gateway.hades.launcher;

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
 * 路由启动类
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月09日 17:10:00
 */
@SpringBootApplication
@ComponentScan(basePackages={"org.banyan.gateway.hades"})
@PropertySource("classpath:properties/hades.properties")
public class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);
    private static volatile boolean RUNNING = true;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Launcher.class);
        Set<ApplicationListener<?>> listeners = builder.application().getListeners();
        listeners.removeIf(listener -> listener instanceof LoggingApplicationListener);
        builder.application().setListeners(listeners);
        builder.run(args);

        LOGGER.info("hades start successfully");

        // 优雅停止项目
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("try to stop the hades");
            synchronized (Launcher.class) {
                RUNNING = false;
                Launcher.class.notify();
            }
        }));

        synchronized (Launcher.class) {
            while (RUNNING) {
                try {
                    Launcher.class.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("stop hades error", e);
                    e.printStackTrace();
                }
            }
        }
    }
}
