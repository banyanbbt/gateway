package org.banyan.gateway.hebe.configuration.dubbo;

import org.banyan.gateway.helios.proto.iface.product.hebe.IPortraitService;
import org.banyan.spring.boot.starter.dubbo.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * DubboConfiguration
 *
 * @author Kevin Huang
 * @since 0.1.0
 * 2018年07月31日 17:20:00
 */
@Configuration
public class DubboConfiguration {

    @Autowired
    private DubboService dubboService;
    @Autowired
    private IPortraitService portraitService;

    @PostConstruct
    public void exportDubboService() {
        this.dubboService.export(IPortraitService.class, this.portraitService);
    }
}