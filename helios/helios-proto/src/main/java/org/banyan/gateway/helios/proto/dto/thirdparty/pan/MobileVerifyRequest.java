package org.banyan.gateway.helios.proto.dto.thirdparty.pan;

import org.banyan.gateway.helios.proto.dto.thirdparty.BaseRequest;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc
 * @date 2018-06-22 11:09:34
 */
public class MobileVerifyRequest extends BaseRequest {

    // 姓名
    private String name;
    // 手机号
    private String mobile;
    // 身份证号
    private String cid;

    public MobileVerifyRequest() {
    }

    public MobileVerifyRequest(String name, String mobile, String cid) {
        this.name = name;
        this.mobile = mobile;
        this.cid = cid;
    }

    public MobileVerifyRequest(String gid, String name, String mobile, String cid) {
        super(gid);
        this.name = name;
        this.mobile = mobile;
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public MobileVerifyRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public MobileVerifyRequest setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getCid() {
        return cid;
    }

    public MobileVerifyRequest setCid(String cid) {
        this.cid = cid;
        return this;
    }
}
