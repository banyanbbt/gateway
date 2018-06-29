package org.banyan.gateway.hades.support;

import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InterfaceConfigMethodArgumentResolver
 * 数据源的解析器
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月20日 14:59:00
 */
public class InterfaceConfigMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return InterfaceConfig.class == parameter.getParameterType();
    }

    @Override
    public InterfaceConfig resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) {
        return new InterfaceConfig().setConfigs(routeConfigs.stream().map(routeConfig -> {
            InterfaceConfig.Item item = new InterfaceConfig.Item()
                    .setIface(Interface.getByIFace(routeConfig.getIface()))
                    .setName(routeConfig.getName())
                    .setChannel(routeConfig.getChannel())
                    .setApiUrl(routeConfig.getApiUrl())
                    .setPrice(routeConfig.getPrice())
                    .setHighestPrice(routeConfig.getHighestPrice())
                    .setLowestPrice(routeConfig.getLowestPrice())
                    .setStatus(routeConfig.isStatus())
                    .setTimeout(routeConfig.getTimeout())
                    .setDescription(routeConfig.getDescription());

            Map<String, String> configs = routeConfig.getInterfaceConfigs();
            if (!CollectionUtils.isEmpty(configs)) {
                item.setConfigs(configs.keySet().stream()
                        .map(ConfigKey::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(x -> x, x -> configs.get(x.getConfig()))));

            }
            return item;
        }).filter(x -> Objects.nonNull(x.getIface())).collect(Collectors.toMap(InterfaceConfig.Item::getIface, x -> x)));
    }
}
