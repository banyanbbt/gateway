package org.banyan.gateway.helios.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ConfigKey
 * config 表的配置
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 16:50:00
 */
public enum ConfigKey {
    ROUTING_KEY("ROUTING_KEY", "PRODUCT", "产品路由配置"),
    IS_PORTRAIT_KEY("IS_PORTRAIT_KEY", "PRODUCT", "产品指标？"),   // 产品是否是画像，用户不可配
    PORTRAIT_KEY("PORTRAIT_KEY", "PRODUCT", "产品指标配置"),
    PRODUCT_INTERFACE_KEY("INTERFACE_KEY", "PRODUCT", "产品服务接口配置"), // 读表()，无需配置

    // 运营商价格配置
    MOBILE_VALIDATE_PRICE_DIANXIN("DX_M_PRICE", "PRODUCT", "手机号验证电信计费价格"),
    MOBILE_VALIDATE_PRICE_YIDONG("YD_M_PRICE", "PRODUCT", "手机号验证移动计费价格"),
    MOBILE_VALIDATE_PRICE_LIANTONG("LT_M_PRICE", "PRODUCT", "手机号验证联通计费价格"),

    MOBILE_VALIDATE_DX_REGEX("DX_MOBILE_REGEX", "PRODUCT", "电信手机号验证正则"),
    MOBILE_VALIDATE_YD_REGEX("YD_MOBILE_REGEX", "PRODUCT", "移动手机号验证正则"),
    MOBILE_VALIDATE_LT_REGEX("LT_MOBILE_REGEX", "PRODUCT", "联通手机号验证正则"),

    MOBILE_VALIDATE_COST_DIANXIN("DX_M_COST", "INTERFACE", "手机号验证电信成本价格"),
    MOBILE_VALIDATE_COST_YIDONG("YD_M_COST", "INTERFACE", "手机号验证移动成本价格"),
    MOBILE_VALIDATE_COST_LIANTONG("LT_M_COST", "INTERFACE", "手机号验证联通成本价格"),

    QUERY_PRICE_WITH_DATA("QUERY_PRICE_WITH_DATA", "PRODUCT", "查询成功有数据计费价格"),
    QUERY_PRICE_NO_DATA("QUERY_PRICE_NO_DATA", "PRODUCT", "查询成功无数据计费价格");

    private String config;
    private String scope; // 作用域：PRODUCT、INTERFACE
    private String msg;

    private static final Map<String, ConfigKey> map = new HashMap<>();

    ConfigKey(String config, String scope, String msg) {
        this.config = config;
        this.scope = scope;
        this.msg = msg;
    }

    public String getConfig() {
        return config;
    }

    public String getScope() {
        return scope;
    }

    public String getMsg() {
        return msg;
    }

    public static ConfigKey get(String config) {
        if (map.isEmpty()) {
            synchronized (map) {
                if (map.isEmpty()) {
                    for (ConfigKey key : ConfigKey.values()) {
                        map.put(key.getConfig(), key);
                    }
                }
            }
        }

        return map.get(null != config ? config.toUpperCase() : null);
    }
}
