package org.banyan.gateway.helios.common;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 响应码和状态码的枚举
 * @date 2017-05-15 18:56:53
 */
public enum SubmitCode {

    // 验证类
    // code 200
    VALIDATE_CONSISTENT("200", "1000", "验证成功"),
    VALIDATE_INCONSISTENT("200", "1001", "验证不一致"),
    VALIDATE_UNSURE("200", "1002", "验证不确定"),
    VALIDATE_FAILED("200", "1003", "验证失败"),
    VALIDATE_NO_RESULT("200", "1004", "库无"),
    VALIDATE_UNSUPPORT("200", "1005", "不支持该笔验证"),
    VALIDATE_TO_MANY_RETRIES("200", "1006", "验证账户尝试次数过多"),
    VALIDATE_MOBILE_UNSUPPORT("200", "1007", "不支持该手机号段"),

    // 查询类
    // code 200
    QUERY_SUCCESS("200", "2000", "查询成功"),
    QUERY_NO_DATA("200", "2001", "没有查询到结果"),
    QUERY_SUBMIT_SUCCESS("200", "2002", "提交成功"),
    QUERY_SUBMIT_FAILED("200", "2003", "提交失败"),
    QUERY_FAILED("200", "2004", "查询失败"),
    QUERY_UNSUPPORT("200", "2005", "不支持该笔查询"),

    // 通用类
    // code 400
    ACCOUNT_FAILED("400", "9800", "账户不存在或被禁用"),
    PATH_FAILED("400", "9801", "访问资源不存在"),
    IP_FAILED("400", "9802", "请求ip没有访问权限"),
    ACCESS_FAILED("400", "9803", "没有此产品访问权限"),
    ARGS_FAILED("400", "9804", "参数为空或格式错误"),
    FEE_FAILED("400", "9805", "该帐号余额不足"),
    CONCURRENCY_FAILED("400", "9806", "此产品请求次数达到上限"),
    DATA_FAILED("400", "9807", "报文解析错误"),
    SIGN_FAILED("400", "9808", "验签失败"),
    TIME_INTERVAL_ERROR("400", "9809", "查询时间区间不正确"),
    PRODUCT_FAILED("400", "9810", "访问产品不存在"),
    PORTRAIT_FAILED("400", "9811", "标签没有权限访问"),
    ENCRYPT_FAILED("400", "9812", "密钥未配置或过期"),
    PRODUCT_CONFIG_FAILED("400", "9813", "产品后台配置异常"),

    // CODE 500
    SYSTEM_ERROR("500", "9900", "系统异常"),
    SERVICE_ERROR("500", "9901", "服务异常"),
    CHANNEL_ERROR("500", "9902", "渠道异常"),
    REQUEST_TIMEOUT("500", "9903", "请求超时");

    public static final String FAILED_CODE = "500";

    private String code; // 响应码
    private String status; // 状态
    private String message; // message

    private SubmitCode(String code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public enum ArgsFailedDetail {
        RECORD_FAILED("account、data、sign为空或格式错误"),
        CUSTOMER_ID_FAILED("客户请求流水号为空或格式错误"),
        PRODUCT_FAILED("数据产品ID为空或格式错误"),
        CUSTOM_FAILED("{0}为空或格式错误"),

        CARD_FAILED("银行卡为空或格式错误"),
        CARD_TYPE_FAILED("账户类型为空或格式错误"),
        MOBILE_FAILED("手机号为空或格式错误"),
        CID_FAILED("身份证为空或格式错误"),
        NAME_FAILED("姓名为空或格式错误");


        private String msg; // msg

        private ArgsFailedDetail(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
