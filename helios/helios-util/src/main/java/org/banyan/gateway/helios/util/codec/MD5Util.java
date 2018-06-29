package org.banyan.gateway.helios.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc md5加密工具类
 * @date 2017-12-25 09:31:48
 */
public class MD5Util {

    /**
     * 加密(MD5)
     *
     * @param data
     * @return 返回加密字串（小写）
     */
    public static String md5Lower(String data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * 加密(MD5)
     *
     * @param data
     * @return 返回加密字串（大写）
     */
    public static String md5Upper(String data) {
        return md5Lower(data).toUpperCase();
    }

}
