package org.banyan.gateway.hades.support;

import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.proto.dto.product.ProductConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductConfigMethodArgumentResolver
 * 产品配置的解析器
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月15日 17:47:00
 */
public class ProductConfigMethodArgumentResolver implements MethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ProductConfig.class == parameter.getParameterType();
    }

    @Override
    public ProductConfig resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) {
        ProductConfig productConfig = new ProductConfig();

        return productConfig.setConfigs(hadesConfig.keySet().stream()
                .map(ConfigKey::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(x -> x, x -> hadesConfig.get(x.getConfig()))));
    }
}