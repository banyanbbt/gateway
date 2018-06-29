package org.banyan.gateway.hades.support;

import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.springframework.core.MethodParameter;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * MethodArgumentResolver
 * 用于将方法参数转换为参数值的
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月15日 17:12:00
 */
public interface MethodArgumentResolver {

    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    boolean supportsParameter(MethodParameter parameter);

    /**
     * Resolves a method parameter into an argument value from a given body and config.
     * @param parameter the method parameter to check
     * @param body request body
     * @param hadesConfig config
     * @return the resolved argument value, or {@code null}
     * @throws Exception in case of errors with the preparation of argument values
     */
    Object resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) throws Exception;
}