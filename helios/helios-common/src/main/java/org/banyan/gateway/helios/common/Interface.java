package org.banyan.gateway.helios.common;


import java.util.HashMap;
import java.util.Map;

import static org.banyan.gateway.helios.common.Channel.*;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Interface 接口列表
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 16:26:00
 */
public enum Interface {
    /**
     * CAUTION: 本枚举值命名规范
     *
     *    AAA_BBB_DDD ("XYZletter", CHANNEL, "description")
     *
     * 说明：
     *
     *    1.枚举值：
     *        AAA: 渠道；
     *        BBB_DDD: 自定义，满足带有接口意义即可；
     *
     *    2.iface：（本字段需满足本类中不重复）
     *        XYZ: 渠道首字母大写；
     *        letter...: 自定义；
     */

    // geo
    BAIDU_GEO_CODER("BDgeocoder", BAIDU, "百度geo coder"),

    // 运营商
    YUNYINGSHANG_MOBILE_VERIFY_3("YYSmobileverify3", YUNYINGSHANG, "运营商三要素认证"),

    // 聚合数据
    JUHESHUJU_VEHICLE_VIOLATIO("JHSJvehicleviolation", JUHESHUJU, "全国车辆违章查询"),

    // 画像类
    YONGHUHUAXIANG_H0101("YHHX_H0101", Channel.YONGHUHUAXIANG, "个人消费类自定画像"),
    YONGHUHUAXIANG_H0201("YHHX_H0201", Channel.YONGHUHUAXIANG, "个人电商消费行为画像"),
    YONGHUHUAXIANG_H0301("YHHX_H0301", Channel.YONGHUHUAXIANG, "个人互联网行为画像"),

    ;

    private String iface;
    private Channel channel;
    private String name;

    Interface(String iface, Channel channel, String name) {
        this.iface = iface;
        this.channel = channel;
        this.name = name;
    }

    public String getIface() {
        return iface;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }


    private static final Map<String, Interface> map = new HashMap<>();
    public static Interface getByIFace(String iface) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    for (Interface item : Interface.values()) {
                        map.put(item.getIface(), item);
                    }
                }
            }
        }

        return map.get(iface);
    }
}
