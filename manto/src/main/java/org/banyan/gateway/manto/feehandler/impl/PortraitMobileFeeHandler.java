package org.banyan.gateway.manto.feehandler.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.helios.common.Operator;
import org.banyan.gateway.helios.common.ThirdpartyFee;
import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;
import org.banyan.gateway.helios.proto.iface.thirdparty.manto.MantoResponse;
import org.banyan.gateway.manto.feehandler.IFeeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 返回计费
 * @date 2018-06-27 18:30:20
 */
public class PortraitMobileFeeHandler implements IFeeHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortraitMobileFeeHandler.class);

    private Map<String, Boolean> statusFeeMap;

    public PortraitMobileFeeHandler() {
        statusFeeMap = new HashMap<>();
        statusFeeMap.put("2000", Boolean.TRUE);
    }

    @Override
    public ThirdpartyFee handleMantoFee(MantoResponse response, Interface interfaze, InterfaceConfig interfaceConfig, Operator operator) {
        ThirdpartyFee fee = response.getThirdpartyFee();
        String status = response.getStatus();
        if (Boolean.TRUE.equals(this.statusFeeMap.get(status))) {
            fee.setFee(Boolean.TRUE);
        }

        if (null != response.getResult()) {
            try {
                JSONArray jsonArray = (JSONArray) JSONObject.toJSON(response.getResult());
                fee.setPrice(interfaceConfig.get(interfaze).getPrice().multiply(BigDecimal.valueOf(jsonArray.size())));
            } catch (Exception e) {
                LOGGER.info("解析结果错误", e);
            }
        }

        return fee;
    }
}
