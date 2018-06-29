package org.banyan.gateway.helios.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 验证类返回的不一致详情
 *
 * @author Norman Hu
 * @since Version
 * <p>
 * 2018/3/15 15:10
 */
public enum VerifySubmitCode {
    SUCCESS_ID_MOBILE("01", "手机号身份证验证一致，手机号姓名验证结果未知"),
    FAILED_ID_MOBILE("02", "手机号身份证验证不一致，手机号姓名验证结果未知"),
    SUCCESS_BUT_MOBILE_NAME("03", "手机号身份证验证一致，手机号姓名验证不一致"),
    FAILED_BUT_MOBILE_NAME("04", "手机号身份证验证不一致，手机号姓名一致"),
    FATAL_ERROR("05", "手机号证件类型非身份证，不再进行验证"),
    SUCCESS_BUT_ID_NAME("06", "身份证和姓名不一致"),
    MOBILE_OFFLINE("07", "手机号T-1月前已离网"),
    FAILED_MOBILE_NAME("08", "手机号码和姓名不一致，手机号码和身份证号码验证结果未知"),
    INCONFORMITY("00", "三维验证不一致");
    private String code;
    private String msg;

    VerifySubmitCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, String> build(){
        Map<String, String> map = new HashMap<>();
        map.put("code",this.code);
        map.put("msg",this.msg);
        return map;
    }
}
