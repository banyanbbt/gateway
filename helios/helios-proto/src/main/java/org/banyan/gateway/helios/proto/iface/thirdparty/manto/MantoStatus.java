package org.banyan.gateway.helios.proto.iface.thirdparty.manto;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 状态码
 * @date 2018-06-27 18:30:16
 */
public enum MantoStatus {

    //请求并解析成功
    SUCCESS,

    //请求失败
    REQUEST_FAILED,

    //返回数据验签失败
    SIGN_NOT_EQUAL,

    //返回数据未加密
    DATA_NOT_ENCRYPT,
}
