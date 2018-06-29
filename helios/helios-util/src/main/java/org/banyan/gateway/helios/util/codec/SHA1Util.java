package org.banyan.gateway.helios.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc sha1加密工具类
 * @date 2017-12-25 09:31:48
 */
public class SHA1Util {

    public static String encrypt(String data) {
        return DigestUtils.sha1Hex(data);
    }

}
