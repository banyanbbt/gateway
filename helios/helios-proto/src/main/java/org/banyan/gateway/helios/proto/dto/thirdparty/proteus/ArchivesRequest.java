package org.banyan.gateway.helios.proto.dto.thirdparty.proteus;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 查询请求体
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/18 09:59
 */
public class ArchivesRequest {
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 姓名
     */
    private String idName;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 银行卡号
     */
    private String bankcardNo;

    public ArchivesRequest() {
    }

    public ArchivesRequest(String idNo, String idName, String phoneNo, String bankcardNo) {
        this.idNo = idNo;
        this.idName = idName;
        this.phoneNo = phoneNo;
        this.bankcardNo = bankcardNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public ArchivesRequest setIdNo(String idNo) {
        this.idNo = idNo;
        return this;
    }

    public String getIdName() {
        return idName;
    }

    public ArchivesRequest setIdName(String idName) {
        this.idName = idName;
        return this;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public ArchivesRequest setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public String getBankcardNo() {
        return bankcardNo;
    }

    public ArchivesRequest setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
        return this;
    }
}
