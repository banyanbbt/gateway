package org.banyan.gateway.hades.support;

import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * 参数解析实例列表的代理 {@link MethodArgumentResolver}s。
 * 支持 {@link MethodParameter} 获取 解析实例的缓存。
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月15日 17:19:00
 */
public class MethodArgumentResolverComposite implements MethodArgumentResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodArgumentResolverComposite.class);

    /**
     * 参数解析实例列表
     */
    private final List<MethodArgumentResolver> argumentResolvers = new LinkedList<>();
    private final Map<MethodParameter, MethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>();

    /**
     * 添加一个参数解析实例 {@link MethodArgumentResolver}.
     */
    public MethodArgumentResolverComposite addResolver(MethodArgumentResolver resolver) {
        this.argumentResolvers.add(resolver);
        return this;
    }

    /**
     * 添加一个参数解析实例 {@link MethodArgumentResolver}.
     */
    public MethodArgumentResolverComposite addResolvers(MethodArgumentResolver... resolvers) {
        if (resolvers != null) {
            this.argumentResolvers.addAll(Arrays.asList(resolvers));
        }
        return this;
    }

    /**
     * 添加一组参数解析实例 {@link MethodArgumentResolver}s.
     */
    public MethodArgumentResolverComposite addResolvers(List<? extends MethodArgumentResolver> resolvers) {
        if (resolvers != null) {
            this.argumentResolvers.addAll(resolvers);
        }
        return this;
    }

    /**
     * 获取只读的参数解析实例
     */
    public List<MethodArgumentResolver> getResolvers() {
        return Collections.unmodifiableList(this.argumentResolvers);
    }

    /**
     * 清空所有参数解析实例
     */
    public void clear() {
        this.argumentResolvers.clear();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (null != getArgumentResolver(parameter));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) throws Exception {
        MethodArgumentResolver resolver = getArgumentResolver(parameter);
        if (null == resolver) {
            LOGGER.info("类型 [{}] 没有支持的解析器", parameter.getGenericParameterType());
            return null;
        }
        return resolver.resolveArgument(parameter, body, hadesConfig, routeConfigs);
    }

    /**
     * 选择解析器
     * @param parameter
     * @return
     */
    private MethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
        MethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            for (MethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
                LOGGER.info("测试解析器 [{}] 是否支持 [{}]", methodArgumentResolver.getClass().getSimpleName(), parameter.getGenericParameterType());
                if (methodArgumentResolver.supportsParameter(parameter)) {
                    result = methodArgumentResolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }
}
