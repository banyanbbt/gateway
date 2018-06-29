package org.banyan.gateway.helios.proto.iface.product.hades;

import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.dto.product.hades.HadesConfig;
import org.banyan.gateway.helios.proto.dto.product.hades.RouteConfig;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * IHadesService
 * hades invoke
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月09日 16:33:00
 */
public interface IRouteService {
    ProductResponse invoke(String body, HadesConfig hadesConfig, List<RouteConfig> routeConfigs);
}