package org.banyan.gateway.helios.common;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 运营商常量
 * @date 2018-04-23 11:29:54
 */
public enum Operator {

    YD("YD", "移动", 0),
    DX("DX", "电信", 1),
    LT("LT", "联通", 2);

    private String symbol;

    private String label;

    private int number;

    private Operator(String symbol, String label, int number) {
        this.symbol = symbol;
        this.label = label;
        this.number = number;
    }

    public String symbol() {
        return this.symbol;
    }

    public String label() {
        return this.label;
    }

    public int number() {
        return this.number;
    }
}
