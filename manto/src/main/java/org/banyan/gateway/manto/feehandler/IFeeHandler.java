package org.banyan.gateway.manto.feehandler;

import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.Operator;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoResponse;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 收费处理接口
 * @date 2018-06-27 18:30:20
 */
public interface IFeeHandler {

    /**
     * 根据返回码生成收费情况
     * @return
     */
    ThirdpartyFee handleMantoFee(MantoResponse response, Interface interfaze, InterfaceConfig interfaceConfig, Operator operator);
}
