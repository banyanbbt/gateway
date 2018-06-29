package org.banyan.gateway.helios.data.jpa.model;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 产品数据来源
 * @date 2018-01-10 10:03:38
 */
public enum SourceType {
    OWN,            // 我方数据
    THIRDPARTY,      // 第三方数据
    MIXED;          // 混合数据
}
