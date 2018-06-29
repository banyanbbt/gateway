package org.banyan.gateway.helios.proto.iface.thirdparty.pan;


import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.pan.MobileVerifyRequest;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 运营商数据源接口
 * @date 2018-01-10 17:21:16
 */
public interface IPanService {

    /**
     * 手机号三要素认证
     * @param request
     * @return
     */
    BaseResponse mobileVerifyThree(MobileVerifyRequest request);
}
