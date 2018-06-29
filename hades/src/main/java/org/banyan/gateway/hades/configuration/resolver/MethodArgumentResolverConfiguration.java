package org.banyan.gateway.hades.configuration.resolver;

import org.banyan.gateway.hades.support.*;
import org.banyan.gateway.hades.support.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MethodArgumentResolverConfiguration
 * 参数解析器配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月16日 09:54:00
 */
@Configuration
public class MethodArgumentResolverConfiguration {

    @Bean
    public MethodArgumentResolver methodArgumentResolverComposite() {
        MethodArgumentResolverComposite methodArgumentResolverComposite = new MethodArgumentResolverComposite();
        return methodArgumentResolverComposite
                .addResolver(new ProductConfigMethodArgumentResolver())
                .addResolver(new InterfaceConfigMethodArgumentResolver())
                .addResolver(new NamedValueMethodArgumentResolver())
                .addResolver(new JacksonMethodArgumentResolver());
    }
}
