package org.banyan.gateway.hades.service;

import com.google.common.base.Stopwatch;
import org.banyan.gateway.hades.model.RouteMethod;
import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.SubmitCode;
import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;
import org.banyan.gateway.helios.proto.iface.product.hades.IRouteService;
import org.banyan.gateway.helios.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * HadesServiceImpl
 * 路由具体实现
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月09日 17:25:00
 */
@Service
public class RouteServiceImpl implements IRouteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);
    private final InvocableHandlerService invocableHandlerService;

    @Autowired
    public RouteServiceImpl(InvocableHandlerService invocableHandlerService) {
        this.invocableHandlerService = invocableHandlerService;
    }

    @Override
    public ProductResponse invoke(String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs) {
        LOGGER.info("路由请求开始");
        Stopwatch stopwatch = Stopwatch.createStarted();
        String serviceConfigs = hadesConfig.get(ConfigKey.PRODUCT_INTERFACE_KEY.getConfig());
        LOGGER.info("路由访问产品配置，{}", serviceConfigs);

        RouteMethod routeMethod = this.getRoute(serviceConfigs);
        LOGGER.info("路由产品配置获取对应的接口，{}", routeMethod);

        if (null == routeMethod) {
            return new ProductResponse().setSubmitCode(SubmitCode.PRODUCT_CONFIG_FAILED);
        }

        ProductResponse response = null;
        try {
            Object result = invocableHandlerService.doInvoke(routeMethod, body, hadesConfig, routeConfigs);
            if (ProductResponse.class.isInstance(result)) {
                response = (ProductResponse) result;
            }
        } catch (Exception e) {
            LOGGER.info("Dubbo获取并请求远程服务异常，接口：" + serviceConfigs, e);
        }

        if (null == response) {
            response = new ProductResponse().setSubmitCode(SubmitCode.SYSTEM_ERROR);
        }

        LOGGER.info("路由返回code: {}，result：{}", response.getSubmitCode(), JacksonUtil.toJson(response.getResult()));
        LOGGER.info("路由请求结束，耗时：{} ms", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        return response;
    }

    /**
     * 获取 {@link RouteMethod}
     */
    private RouteMethod getRoute(String serviceConfigs) {
        RouteMethod routeMethod = null;
        if (!StringUtils.isEmpty(serviceConfigs)) {
            String[] array = serviceConfigs.split(Constants.JING_HAO);
            if (array.length == 2) {
                String iface = array[0];
                String method = array[1];
                routeMethod = invocableHandlerService.getRouteMethod(iface, method);
            }

        }
        return routeMethod;
    }
}
