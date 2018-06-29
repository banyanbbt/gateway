package org.banyan.gateway.helios.proto.dto.thirdparty.doris;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc geo coder请求对象
 * @date 2018-06-21 19:19:44
 */
public class GeoCoderRequest extends BaseRequest {

    // 地址
    private String address;

    public GeoCoderRequest() {
    }

    public GeoCoderRequest(String address) {
        this.address = address;
    }

    public GeoCoderRequest(String gid, String address) {
        super(gid);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public GeoCoderRequest setAddress(String address) {
        this.address = address;
        return this;
    }
}
