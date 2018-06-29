package org.banyan.gateway.helios.proto.iface.product.hydra;

import org.banyan.gateway.helios.proto.dto.product.ProductResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.ares.VehicleViolationRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.doris.GeoCoderRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.pan.MobileVerifyRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc hydra服务
 * @date 2018-06-22 10:30:03
 */
public interface IHydraService {

    /**
     * 百度geo coder
     * @param request
     * @return
     */
    ProductResponse geoCoder(GeoCoderRequest request);

    /**
     * 聚合数据全国查违章
     * @param request
     * @return
     */
    ProductResponse vehicleViolation(VehicleViolationRequest request);

    /**
     * 手机号三要素认证
     * @param request
     * @return
     */
    ProductResponse mobileVerifyThree(MobileVerifyRequest request);
}
