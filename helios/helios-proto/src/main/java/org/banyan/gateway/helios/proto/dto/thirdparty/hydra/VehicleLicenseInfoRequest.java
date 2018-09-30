package org.banyan.gateway.helios.proto.dto.thirdparty.hydra;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 行驶证信息多项验证请求体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/9/5 15:38
 */
public class VehicleLicenseInfoRequest {

    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 号牌种类
     */
    private String plateType;

    public VehicleLicenseInfoRequest() {
    }

    public VehicleLicenseInfoRequest(String plateNumber, String plateType) {
        this.plateNumber = plateNumber;
        this.plateType = plateType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public VehicleLicenseInfoRequest setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public String getPlateType() {
        return plateType;
    }

    public VehicleLicenseInfoRequest setPlateType(String plateType) {
        this.plateType = plateType;
        return this;
    }

    @Override
    public String toString() {
        return "VehicleLicenseInfoRequest{" + "plateNumber='" + plateNumber + '\'' + ", plateType='" + plateType + '\'' + '}';
    }
}
