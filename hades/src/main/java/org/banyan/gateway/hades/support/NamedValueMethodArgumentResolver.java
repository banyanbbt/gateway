package org.banyan.gateway.hades.support;

import com.fasterxml.jackson.core.type.TypeReference;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * NamedValueMethodArgumentResolver
 * 基本数据类型装换
 *
 * @author Kevin Huang
 * @since version
 * 2018年05月23日 11:38:00
 */
public class NamedValueMethodArgumentResolver implements MethodArgumentResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonMethodArgumentResolver.class);
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return BeanUtils.isSimpleProperty(parameter.getNestedParameterType()) || Collection.class.isAssignableFrom(parameter.getNestedParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) throws Exception {
        Object arg = null;

        parameter.initParameterNameDiscovery(parameterNameDiscoverer);
        String name = parameter.getParameterName();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Map<String, Object> params = JacksonUtil.toObject(body, new TypeReference<Map<String, Object>>(){});
        if (!CollectionUtils.isEmpty(params)) {
            arg = params.get(name);
        }
        if (null == arg) {
            arg = this.handleNullValue(name, arg, nestedParameter.getNestedParameterType());
        }

        // TODO 处理基本数据类型
        try {
            arg = typeConverter.convertIfNecessary(arg, parameter.getParameterType(), parameter);
        } catch (TypeMismatchException typeMismatchException) {
            LOGGER.info("转换参数类型异常: arg:{}, requiredType:{}, name:{}, parameter:{}, cause:{}", arg, typeMismatchException.getRequiredType(), name, parameter, typeMismatchException.getCause());
        } catch (Exception e) {
            LOGGER.info("转换参数类型异常", e);
        }
        return arg;
    }

    private Object handleNullValue(String name, Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            }

            if (paramType.isPrimitive()) {
                throw new IllegalStateException("Optional " + paramType.getSimpleName() + " parameter '" + name + "' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }

        return value;
    }

}
