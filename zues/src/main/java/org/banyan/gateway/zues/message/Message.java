package org.banyan.gateway.zues.message;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 返回客户的对象
 * @date 2017-05-15 19:14:10
 */
public class Message {

    // 数据主体是否加密标识（当请求用户的账号无法获取时，数据主体不加密，该字段为false）
    private boolean encrypt;

    // 数据主体（数据主体原始响应内容由具体接口定义数据格式）
    private String data;

    // 签名（采用MD5加密account、data 两个参数的值组成的字符串）
    private String sign;

    public Message() {

    }

    public Message(boolean encrypt, String data, String sign) {
        this.encrypt = encrypt;
        this.data = data;
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Message{" +
                "encrypt=" + encrypt +
                ", data='" + data + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        if (encrypt != that.encrypt) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return sign != null ? sign.equals(that.sign) : that.sign == null;
    }

    @Override
    public int hashCode() {
        int result = (encrypt ? 1 : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        return result;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public Message setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
        return this;
    }

    public String getData() {
        return data;
    }

    public Message setData(String data) {
        this.data = data;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public Message setSign(String sign) {
        this.sign = sign;
        return this;
    }
}
