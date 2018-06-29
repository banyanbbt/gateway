package org.banyan.gateway.hydra.launcher;

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
 *
 * @author levi
 * @version 0.0.1
 * @desc 画像服务启动器
 * @date 2018-02-27 16:28:32
 */
@SpringBootApplication
@ComponentScan(basePackages={"org.banyan.gateway.hydra"})
@PropertySource("classpath:properties/hydra.properties")
public class Launcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);
    private static volatile boolean RUNNING = true;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Launcher.class);
        Set<ApplicationListener<?>> listeners = builder.application().getListeners();
        listeners.removeIf(listener -> listener instanceof LoggingApplicationListener);
        builder.application().setListeners(listeners);
        builder.run(args);

        LOGGER.info("hydra start successfully");

        // 优雅停止项目
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("try to stop the system");
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
                    LOGGER.error("wait error");
                    e.printStackTrace();
                }
            }
        }
    }
}
