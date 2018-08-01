package org.banyan.gateway.helios.proto.iface.product.hebe;

import org.banyan.gateway.helios.proto.dto.product.InterfaceConfig;
import org.banyan.gateway.helios.proto.dto.product.ProductResponse;

import java.util.List;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * 用户画像接口
 * IPortraitService
 *
 * @author Kevin Huang
 * @since 0.1.0
 * 2018年07月31日 17:26:00
 */
public interface IPortraitService {
    /**
     * 金融类消费标签
     * @param card 卡号
     * @param name 姓名
     * @param cid 身份证
     * @param mobile 手机号
     * @param portraits 标签列表
     * @param interfaceConfig 数据源配置
     * @return 返回标签值
     */
    ProductResponse pH0101(String card, String name, String cid, String mobile, List<String> portraits, InterfaceConfig interfaceConfig);

    /**
     * 电商消费行为评级
     * @param name 姓名
     * @param cid 身份证
     * @param mobile 手机号
     * @param portraits 标签列表
     * @param interfaceConfig 数据源配置
     * @return 返回标签值
     */
    ProductResponse pH0201(String name, String cid, String mobile, List<String> portraits, InterfaceConfig interfaceConfig);

    /**
     * 互联网行为自定画像
     * @param mobile 手机号
     * @param portraits 标签列表
     * @param interfaceConfig 数据源配置
     * @return 返回标签值
     */
    ProductResponse pH0301(String mobile, List<String> portraits, InterfaceConfig interfaceConfig);
}
