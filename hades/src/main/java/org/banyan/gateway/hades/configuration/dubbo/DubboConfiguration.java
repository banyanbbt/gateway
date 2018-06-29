package org.banyan.gateway.hades.configuration.dubbo;

import org.banyan.gateway.helios.proto.iface.product.hades.IRouteService;
import org.banyan.spring.boot.starter.dubbo.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * DubboConfiguration
 * dubbo配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月09日 17:30:00
 */
@Configuration
public class DubboConfiguration {
    private final DubboService dubboService;
    private final IRouteService routeService;

    @Autowired
    public DubboConfiguration(DubboService dubboService, IRouteService routeService) {
        this.dubboService = dubboService;
        this.routeService = routeService;
    }

    @PostConstruct
    public void exportDubboService() {
        this.dubboService.export(IRouteService.class, this.routeService);
    }

}
