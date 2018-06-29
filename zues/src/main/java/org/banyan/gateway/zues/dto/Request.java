package org.banyan.gateway.zues.dto;

import java.io.Serializable;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 接口统一输入参数
 * @date 2017-05-11 17:29:10
 */
public class Request implements Serializable {
    private static final long serialVersionUID = -1066960877676580906L;

    // 账户号（我方分配给客户账户号）
    private String account;

    // 密文数据主体（参数主体JSON数据 RSA加密后字符串），参数主体JSON数据由具体接口定义数据格式
    private String data;

    // 签名（采用MD5加密account、data两个参数的值组成的字符串）
    private String sign;

    public Request() {

    }

    @Override
    public String toString() {
        return "Request{" +
                "account='" + account + '\'' +
                ", data='" + data + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request that = (Request) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return sign != null ? sign.equals(that.sign) : that.sign == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        return result;
    }

    public String getAccount() {
        return account;
    }

    public Request setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getData() {
        return data;
    }

    public Request setData(String data) {
        this.data = data;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public Request setSign(String sign) {
        this.sign = sign;
        return this;
    }
}
