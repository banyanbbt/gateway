package org.banyan.gateway.hades.service;

import org.banyan.gateway.hades.model.RouteMethod;
import org.banyan.gateway.hades.support.MethodArgumentResolver;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.banyan.spring.boot.starter.dubbo.DubboService;
import org.banyan.spring.boot.starter.dubbo.DubboSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * InvocableHandlerService
 * 根据产品配置 service method 反射服务
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月14日 11:11:00
 */
@Service
public class InvocableHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvocableHandlerService.class);
    private final ProtoInterfaceService protoInterfaceService;
    private final MethodArgumentResolver methodArgumentResolverComposite;
    private final DubboService dubboService;
    // interface -> method -> Method
    private ConcurrentMap<String, ConcurrentMap<String, RouteMethod>> interfaceMethod = new ConcurrentHashMap<>();

    @Autowired
    public InvocableHandlerService(ProtoInterfaceService protoInterfaceService, MethodArgumentResolver methodArgumentResolverComposite, DubboService dubboService) {
        this.protoInterfaceService = protoInterfaceService;
        this.methodArgumentResolverComposite = methodArgumentResolverComposite;
        this.dubboService = dubboService;
    }

    /**
     * Invoke the handler method with the given argument values.
     */
    Object doInvoke(RouteMethod routeMethod, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) {
        ReflectionUtils.makeAccessible(routeMethod.getMethod());
        try {
            Object[] args = this.getMethodArgumentValues(routeMethod, body, hadesConfig, routeConfigs);
            return routeMethod.getMethod().invoke(getBean(routeMethod), args);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to invoke handler method", e);
        }
    }

    /**
     * Get the method argument values for the current Method.
     */
    private Object[] getMethodArgumentValues(RouteMethod routeMethod, String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) throws Exception {
        MethodParameter[] parameters = routeMethod.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];

            if (methodArgumentResolverComposite.supportsParameter(parameter)) {
                try {
                    args[i] = methodArgumentResolverComposite.resolveArgument(parameter, body, hadesConfig, routeConfigs);
                    continue;
                } catch (Exception ex) {
                    LOGGER.info("无法转化方法[{}]的第{}位置的参数[{}]", parameter.getMethod().toGenericString(), i, parameter);
                    throw ex;
                }
            }
            if (args[i] == null) {
                throw new IllegalStateException("无法转化方法[" + parameter.getMethod().toGenericString() + "]的第" +i + "位置的参数: " + parameter);
            }
        }

        return args;
    }

    private Object getBean(RouteMethod routeMethod) {
        Object object = null;
        try {
            object = dubboService.get(routeMethod.getBeanType());
        } catch (DubboSystemException e) {
            LOGGER.info("获取远程服务异常", e);
            throw e;
        }
        return object;
    }

    /**
     * 根据接口名和方法名获取Method对象
     * @param ifaceName
     * @param method
     * @return
     */
    public RouteMethod getRouteMethod(String ifaceName, String method) {
        RouteMethod routeMethod = null;

        // 从缓存中获取RouteMethod
        ConcurrentMap<String, RouteMethod> methodMap = interfaceMethod.get(ifaceName);
        if (null != methodMap) {
            routeMethod = methodMap.get(method);
        }

        // 创建RouteMethod
        if (null == routeMethod) {
            LOGGER.info("首次加载类：{}反射方法：{}", ifaceName, method);
            routeMethod = loadRouteMethod(ifaceName, method);
            if (null != routeMethod) {
                if (null == methodMap) {
                    methodMap = new ConcurrentHashMap<>();
                    interfaceMethod.put(ifaceName, methodMap);
                }
                methodMap.put(method, routeMethod);
            }
        }

        return routeMethod;
    }

    /**
     * 根据接口名和方法名加载Method对象
     * @param ifaceName
     * @param method
     * @return
     */
    private RouteMethod loadRouteMethod(String ifaceName, String method) {
        RouteMethod route = null;

        Class<?> iface = null;
        try {
            iface = protoInterfaceService.getInterface(ifaceName);
            if (null != iface) {
                route = new RouteMethod(ifaceName, method, iface);
            }
        } catch (Exception e) {
            LOGGER.info("获取Method异常", e);
        }
        return route;
    }
}
