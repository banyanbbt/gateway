package org.banyan.gateway.hades.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * JacksonMethodArgumentResolver
 * 使用jackson转对象的解析器
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月15日 17:50:00
 */
public class JacksonMethodArgumentResolver implements MethodArgumentResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonMethodArgumentResolver.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) {
        Object object = null;
        try {
            object = objectMapper.readValue(body, parameter.getParameterType());
        } catch (Exception e) {
            LOGGER.info("JSON字符串转为对象出错", e);
        }
        return object;
    }
}
