package org.banyan.gateway.helios.proto.dto.thirdparty;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 基础第三方数据源请求对象
 * @date 2018-06-21 19:16:05
 */
public class BaseRequest {

    // 请求跟踪号
    private String gid;

    public BaseRequest() {
    }

    public BaseRequest(String gid) {
        this.gid = gid;
    }

    public String getGid() {
        return gid;
    }

    public BaseRequest setGid(String gid) {
        this.gid = gid;
        return this;
    }
}
