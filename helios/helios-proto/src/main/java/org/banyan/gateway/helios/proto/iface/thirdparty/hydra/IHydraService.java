package org.banyan.gateway.helios.proto.iface.thirdparty.hydra;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.hydra.DrivingQuiryInfoRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.hydra.VehicleLicenseInfoRequest;
import org.banyan.gateway.helios.proto.dto.thirdparty.hydra.VehicleViolationInquiryInfoRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 驾驶证信息查询
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/9/5 15:01
 */
public interface IHydraService {

    /**
     * 驾驶证信息查询
     *
     * @param drivingQuiryInfoRequest 请求参数
     * @return 接口返回数据
     */
    BaseResponse drivingQuiryInfo(DrivingQuiryInfoRequest drivingQuiryInfoRequest);

    /**
     * 行驶证信息多项验证
     *
     * @param vehicleLicenseInfoRequest 请求参数
     * @return 接口返回数据
     */
    BaseResponse vehicleLicenseInfo(VehicleLicenseInfoRequest vehicleLicenseInfoRequest);

    /**
     * 车辆违章信息查询
     *
     * @param vehicleViolationInquiryInfoRequest 请求参数
     * @return 查询结果
     */
    BaseResponse vehicleViolationInquiryInfo(VehicleViolationInquiryInfoRequest vehicleViolationInquiryInfoRequest);
}
