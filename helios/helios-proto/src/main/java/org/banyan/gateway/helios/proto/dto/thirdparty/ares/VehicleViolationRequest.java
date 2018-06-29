package org.banyan.gateway.helios.proto.dto.thirdparty.ares;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc
 * @date 2018-06-22 11:09:34
 */
public class VehicleViolationRequest extends BaseRequest {

    // 查询的城市
    private String city;
    // 车牌号
    private String plateNo;
    // 号牌类型
    private String plateType;
    // 发动机号
    private String engineNo;
    // 车架号
    private String vin;

    public VehicleViolationRequest() {
    }

    public VehicleViolationRequest(String city, String plateNo, String plateType, String engineNo, String vin) {
        this.city = city;
        this.plateNo = plateNo;
        this.plateType = plateType;
        this.engineNo = engineNo;
        this.vin = vin;
    }

    public VehicleViolationRequest(String gid, String city, String plateNo, String plateType, String engineNo, String vin) {
        super(gid);
        this.city = city;
        this.plateNo = plateNo;
        this.plateType = plateType;
        this.engineNo = engineNo;
        this.vin = vin;
    }

    public String getCity() {
        return city;
    }

    public VehicleViolationRequest setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public VehicleViolationRequest setPlateNo(String plateNo) {
        this.plateNo = plateNo;
        return this;
    }

    public String getPlateType() {
        return plateType;
    }

    public VehicleViolationRequest setPlateType(String plateType) {
        this.plateType = plateType;
        return this;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public VehicleViolationRequest setEngineNo(String engineNo) {
        this.engineNo = engineNo;
        return this;
    }

    public String getVin() {
        return vin;
    }

    public VehicleViolationRequest setVin(String vin) {
        this.vin = vin;
        return this;
    }
}
