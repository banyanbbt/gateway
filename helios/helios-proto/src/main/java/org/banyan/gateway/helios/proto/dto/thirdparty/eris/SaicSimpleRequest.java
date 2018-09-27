package org.banyan.gateway.helios.proto.dto.thirdparty.eris;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 工商基本信息查询体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/26 11:48
 */
public class SaicSimpleRequest {
    /**
     * 查询关键字
     */
    private String key;
    /**
     * 查询关键字类型(2-企业名称; 3-企业注册号)
     */
    private String keyType;

    public SaicSimpleRequest() {
    }

    public SaicSimpleRequest(String key, String keyType) {
        this.key = key;
        this.keyType = keyType;
    }

    public String getKey() {
        return key;
    }

    public SaicSimpleRequest setKey(String key) {
        this.key = key;
        return this;
    }

    public String getKeyType() {
        return keyType;
    }

    public SaicSimpleRequest setKeyType(String keyType) {
        this.keyType = keyType;
        return this;
    }
}
