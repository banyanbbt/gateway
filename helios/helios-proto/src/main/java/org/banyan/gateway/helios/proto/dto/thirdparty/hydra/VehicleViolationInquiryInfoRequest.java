package org.banyan.gateway.helios.proto.dto.thirdparty.hydra;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 车辆违章信息查询参数体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/9/5 15:59
 */
public class VehicleViolationInquiryInfoRequest {

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 发动机号
     */
    private String engineNumber;

    /**
     * 号牌种类
     */
    private String plateType;

    @Override
    public String toString() {
        return "VehicleViolationInquiryInfoRequest{" + "plateNumber='" + plateNumber + '\'' + ", vin='" + vin + '\'' + ", engineNumber='" + engineNumber + '\'' + ", plateType='" + plateType + '\'' + '}';
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public VehicleViolationInquiryInfoRequest setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public String getVin() {
        return vin;
    }

    public VehicleViolationInquiryInfoRequest setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public VehicleViolationInquiryInfoRequest setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
        return this;
    }

    public String getPlateType() {
        return plateType;
    }

    public VehicleViolationInquiryInfoRequest setPlateType(String plateType) {
        this.plateType = plateType;
        return this;
    }
}
