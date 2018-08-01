package org.banyan.gateway.helios.common;

import java.nio.charset.Charset;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 常量
 * @date 2017-12-25 09:31:48
 */
public class Constants {

    // 标点符号
    public static final String LIKE = "like";
    public static final String PERCENT = "%";
    public static final String EQUAL = "=";
    public static final String GREATER = ">";
    public static final String GEQUAL = ">=";
    public static final String LESS = "<";
    public static final String LEQUAL = "<=";
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String POINT = ".";
    public static final String MIDDLE_LINE = "-";
    public static final String UNDER_LINE = "_";
    public static final String LINE_FEED = "\n";
    public static final String SPACE = " ";
    public static final String SINGLE_PHE = "'";
    public static final String LEFT_MIDDLE_BRAC = "[";
    public static final String RIGHT_MIDDLE_BRAC = "]";
    public static final String COLON = ":";
    public static final String VERTICAL_LINE = "|";
    public static final String VERTICAL_LINE_SPLIT = "\\|";
    public static final String XING_HAO = "*";
    public static final String JING_HAO = "#";
    public static final String JIAN_TOU = "->";
    public static final String QUESTION_MARK = "?";
    public static final String AND = "&";

    public static final String CHARSET_UTF8_STR = "UTF-8";
    public static final Charset CHARSET_UTF8 = Charset.forName(CHARSET_UTF8_STR);
    public static final String EMPTY_STR = "";
    public static final String ENCRYPT_MD5 = "md5";

    public static final String PORTRAIT_ALL = "all";
    public static final String TRUE_STRING = "true";
    public static final String FALSE_STRING = "false";

    public static final String COMMA_JING_HAO_REGEX = "[,#]";


    // 请求数据相关
    public static final String CODE = "code";
    public static final String PRODUCT_ID = "productId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String PORTRAITS = "portraits";

    public static final String CARD = "card";
    public static final String PRODUCT = "product";
    public static final String GID = "gid";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String RESULT = "result";

    // 验签相关
    public static final String ACCOUNT = "account";
    public static final String DATA = "data";
    public static final String ENCRYPT = "encrypt";
    public static final String SIGN = "sign";

    public static final String UNKNOWN = "unknown";
    public static final int LIMITLESS_LENGTH = -1;

    // 请求唯一跟踪号的key
    public static final String TRACKID = "TRACKID";

    // portrait config key price prefix
    public static final String PORTRAIT_CONFIG_KEY_PRICE_PREFIX = "PORTRAIT_PRICE_";
    // portrait config key cost prefix
    public static final String PORTRAIT_CONFIG_KEY_COST_PREFIX = "XL_PORTRAIT_COST_";
    // operator config key cost suffix
    public static final String OPERATOR_CONFIG_KEY_COST_SUFFIX = "_M_COST";
    // operator config key price suffix
    public static final String OPERATOR_CONFIG_KEY_PRICE_SUFFIX = "_M_PRICE";
    // operator config key regex suffix
    public static final String OPERATOR_CONFIG_KEY_REGEX_SUFFIX = "_MOBILE_REGEX";

    // global cache name
    public static final String CACHE_NAME = "greek-ifaces-tokens";
}
