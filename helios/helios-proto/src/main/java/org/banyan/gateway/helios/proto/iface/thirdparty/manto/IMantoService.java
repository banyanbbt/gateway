package org.banyan.gateway.helios.proto.iface.thirdparty.manto;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 用户画像数据源接口
 * @date 2018-06-27 17:21:16
 */
public interface IMantoService {

    /**
     * 请求用户画像数据源
     * @param request
     * @return
     */
    MantoResponse callMantoService(MantoRequest request);
}
