package org.banyan.gateway.helios.util;


import org.joda.time.DateTime;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 日期工具类工具类
 * @date 2017-12-25 09:31:48
 */
public class DateUtil {

    /**
     * 获取yyyyMMddHHmmss格式字符串
     * @return 固定格式字符串
     */
    public static String getDateTime() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyyMMddHHmmss");
    }

    /**
     * 获取yyyyMMdd格式字符串
     * @return 固定格式字符串
     */
    public static String getDate() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyyMMdd");
    }

    /**
     * 获取HHmmss格式字符串
     * @return 固定格式字符串
     */
    public static String getTime() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("HHmmss");
    }

    /**
     * 获取yyyyMMddHHmmssSSS日期字符串
     * @return 日期字符串
     */
    public static String getDateTimeSecond() {
        DateTime dateTime = new DateTime();
        return dateTime.toString("yyyyMMddHHmmssSSS");
    }
}
