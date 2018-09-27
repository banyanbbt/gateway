package org.banyan.gateway.helios.proto.dto.thirdparty.eris;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 个人风险信息查询主体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/26 14:34
 */
public class RiskPersonlistVipRequest {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idNo;

    private String pageNo;

    public RiskPersonlistVipRequest() {
    }

    public RiskPersonlistVipRequest(String name, String idNo, String pageNo) {
        this.name = name;
        this.idNo = idNo;
        this.pageNo = pageNo;
    }

    public String getName() {
        return name;
    }

    public RiskPersonlistVipRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdNo() {
        return idNo;
    }

    public RiskPersonlistVipRequest setIdNo(String idNo) {
        this.idNo = idNo;
        return this;
    }

    public String getPageNo() {
        return pageNo;
    }

    public RiskPersonlistVipRequest setPageNo(String pageNo) {
        this.pageNo = pageNo;
        return this;
    }
}
