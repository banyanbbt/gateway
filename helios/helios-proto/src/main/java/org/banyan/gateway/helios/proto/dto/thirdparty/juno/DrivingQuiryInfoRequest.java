package org.banyan.gateway.helios.proto.dto.thirdparty.juno;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 驾驶证接口请求参数
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/9/5 15:29
 */
public class DrivingQuiryInfoRequest {
    private String name;

    private String did;

    public DrivingQuiryInfoRequest() {
    }

    public DrivingQuiryInfoRequest(String name, String did) {
        this.name = name;
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public DrivingQuiryInfoRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDid() {
        return did;
    }

    public DrivingQuiryInfoRequest setDid(String did) {
        this.did = did;
        return this;
    }

    @Override
    public String toString() {
        return "DrivingQuiryInfoRequest{" + "name='" + name + '\'' + ", did='" + did + '\'' + '}';
    }
}
