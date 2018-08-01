package org.banyan.gateway.manto.feehandler;

import org.banyan.gateway.helios.common.Interface;
import org.banyan.gateway.manto.feehandler.impl.PortraitMobileFeeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 工厂类
 * @date 2018-06-27 18:30:20
 */
public class FeeHandlerFactory {
    private static final Map<Interface, IFeeHandler> handlerInstancesMap = new HashMap<>();

    static {
        handlerInstancesMap.put(Interface.YONGHUHUAXIANG_H0101, new PortraitMobileFeeHandler());
        handlerInstancesMap.put(Interface.YONGHUHUAXIANG_H0201, new PortraitMobileFeeHandler());
        handlerInstancesMap.put(Interface.YONGHUHUAXIANG_H0301, new PortraitMobileFeeHandler());
    }

    public static IFeeHandler getFeeHandler(Interface interfaze) {
        return handlerInstancesMap.get(interfaze);
    }
}
