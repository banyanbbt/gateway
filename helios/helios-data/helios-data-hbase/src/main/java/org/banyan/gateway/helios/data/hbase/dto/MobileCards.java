package org.banyan.gateway.helios.data.hbase.dto;

import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 手机号卡号对应关系
 * @date 2017-12-27 09:31:48
 */
public class MobileCards {

    // 手机号
    private String mobile;
    // 手机号对应的卡号列表
    private List<String> cards;

    public String getMobile() {
        return mobile;
    }

    public MobileCards setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public List<String> getCards() {
        return cards;
    }

    public MobileCards setCards(List<String> cards) {
        this.cards = cards;
        return this;
    }
}
