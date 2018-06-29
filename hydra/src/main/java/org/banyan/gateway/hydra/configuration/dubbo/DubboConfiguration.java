package org.banyan.gateway.hydra.configuration.dubbo;

import org.banyan.gateway.helios.proto.iface.product.hydra.IHydraService;
import org.banyan.spring.boot.starter.dubbo.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc dubbo配置
 * @date 2018-02-27 16:35:00
 */
@Configuration
public class DubboConfiguration {

    @Autowired
    private DubboService dubboService;
    @Autowired
    private IHydraService hydraService;

    @PostConstruct
    public void exportDubboService() {
        this.dubboService.export(IHydraService.class, this.hydraService);
    }
}
