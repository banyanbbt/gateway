package org.banyan.gateway.poseidon.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Launcher
 * 计费项目启动类
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月21日 15:40:00
 */
@SpringBootApplication
@ComponentScan(basePackages={"org.banyan.gateway.poseidon", "org.banyan.gateway.helios.data.jpa", "org.banyan.gateway.helios.data.mongo.service"})
@PropertySource("classpath:properties/poseidon.properties")
public class Launcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);
    private static volatile boolean RUNNING = true;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Launcher.class);
        Set<ApplicationListener<?>> listeners = builder.application().getListeners();
        listeners.removeIf(listener -> listener instanceof LoggingApplicationListener);
        builder.application().setListeners(listeners);
        ConfigurableApplicationContext context = builder.run(args);

        LOGGER.info("poseidon start successfully");

        // 优雅停止项目
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("try to stop the system");
            synchronized (Launcher.class) {
                ThreadPoolExecutor executor = context.getBean(ThreadPoolExecutor.class);

                while (executor.getActiveCount() > 0) {
                    LOGGER.error("停止项目，当前线程池运行中的线程的数量：{}", executor.getActiveCount());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        LOGGER.error("检测线程池异常", e);
                    }
                }

                RUNNING = false;
                Launcher.class.notify();
            }
        }));

        synchronized (Launcher.class) {
            while (RUNNING) {
                try {
                    Launcher.class.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("stop error", e);
                }
            }
        }
    }
}