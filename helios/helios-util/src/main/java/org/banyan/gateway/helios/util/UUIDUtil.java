package org.banyan.gateway.helios.util;

import java.util.UUID;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc uuid工具类
 * @date 2017-12-25 09:31:48
 */
public class UUIDUtil {

    /**
     * 生产uuid字符串
     * @return uuid字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
