package org.banyan.gateway.helios.common;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 渠道名字
 * @date 2017-09-08 09:14:23
 */
public enum Channel {

    JUHESHUJU("juheshuju", "聚合数据"),
    YUNYINGSHANG("yunyingshang", "运营商"),
    BAIDU("baidu", "百度"),
    YONGHUHUAXIANG("yonghuhuaxiang", "用户画像"),
    XINDAI("xindai", "信用贷款"),
    GONGSHANG("gongshang", "工商信息"),
    CHELIANG("cheliang", "车辆相关信息"),
    ;

    private String symbol;

    private String label;

    Channel(String symbol, String label) {
        this.symbol = symbol;
        this.label = label;
    }

    public String symbol() {
        return this.symbol;
    }

    public String label() {
        return this.label;
    }
}
