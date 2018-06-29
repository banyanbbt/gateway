package org.banyan.gateway.helios.proto.iface.thirdparty.ares;


import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.ares.VehicleViolationRequest;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 百度数据源接口
 * @date 2018-01-10 17:21:16
 */
public interface IAresService {

    /**
     * 查询全国违章
     * @param request
     * @return
     */
    BaseResponse vehicleViolation(VehicleViolationRequest request);
}
