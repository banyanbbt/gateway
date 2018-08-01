package org.banyan.gateway.helios.common;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 请求参数枚举
 * @date 2018-06-27 18:23:16
 */
public enum RequestField {
    CID("cid", "身份证号"),
    NAME("name", "姓名"),
    MOBILE("mobile", "手机号"),
    CARD("card", "银行卡号"),
    PORTRAIT("portraits", "标签"),
    PLATENO("plateNo", "车牌号"),
    VIN("vin", "车辆识别码"),
    ORG_NAME("orgName", "企业名称"),
    ORG_REG_NO("orgRegNo", "企业注册码"),
    ADS("ads", "查询地址"),
    ADSTYPE("adsType", "查询类型,如下：work:工作 home:家"),
    LAT("lat", "纬度，如：39.980869（支持百度坐标系）"),
    LNG("lng", "经度，如：116.404844（支持百度坐标系）"),
    ;


    RequestField(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
