package org.banyan.gateway.helios.proto.iface.thirdparty.doris;


import org.banyan.gateway.helios.proto.dto.thirdparty.BaseResponse;
import org.banyan.gateway.helios.proto.dto.thirdparty.doris.GeoCoderRequest;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 百度数据源接口
 * @date 2018-01-10 17:21:16
 */
public interface IDorisService {

    /**
     * 根据address查询经纬度
     * @param request
     * @return
     */
    BaseResponse geoCoder(GeoCoderRequest request);
}
