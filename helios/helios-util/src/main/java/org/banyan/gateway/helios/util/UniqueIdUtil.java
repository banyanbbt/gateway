package org.banyan.gateway.helios.util;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 分布式全局唯一id
 * @date 2018-01-15 21:36:26
 */
public class UniqueIdUtil {

    /**
     * 唯一id
     * @return
     */
    public static String getUniqueId() {
        return UUIDUtil.uuid();
    }
}
