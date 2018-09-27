package org.banyan.gateway.helios.proto.dto.thirdparty.eris;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 企业风险概要查询主体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/26 13:11
 */
public class RiskListVipRequest {
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 类型
     */
    private String dataType;
    /**
     * 页码
     */
    private String pageNo;
    /**
     * 数据自身的时间
     */
    private String sortTime;
    /**
     * 数据采集时间
     */
    private String crawlTime;

    public RiskListVipRequest() {
    }

    public RiskListVipRequest(String entName, String dataType, String pageNo, String sortTime, String crawlTime) {
        this.entName = entName;
        this.dataType = dataType;
        this.pageNo = pageNo;
        this.sortTime = sortTime;
        this.crawlTime = crawlTime;
    }

    public String getEntName() {
        return entName;
    }

    public RiskListVipRequest setEntName(String entName) {
        this.entName = entName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public RiskListVipRequest setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getPageNo() {
        return pageNo;
    }

    public RiskListVipRequest setPageNo(String pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public String getSortTime() {
        return sortTime;
    }

    public RiskListVipRequest setSortTime(String sortTime) {
        this.sortTime = sortTime;
        return this;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public RiskListVipRequest setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime;
        return this;
    }
}
