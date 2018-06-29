package org.banyan.gateway.helios.data.jpa.domain.product;

import org.banyan.gateway.helios.data.jpa.converter.HeliosDataJpaConverters;
import org.banyan.gateway.helios.data.jpa.model.EncryptType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 系统的参数表
 * @date 2018-01-03 11:10:12
 */
@Entity
@Table(name = "field")
public class Field implements Serializable {
    private static final long serialVersionUID = -7797055003860272660L;
    // 参数
    @Id
    @Column(name = "field", nullable = false)
    private String field;
    // 参数名称
    @Column(name = "name", nullable = false)
    private String name;
    // 字符串最大长度，数字最大值
    @Column(name = "max_len")
    private Integer maxLength;
    // 字符串最小长度，数字最小值
    @Column(name = "min_len")
    private Integer minLength;
    // 正则表达式
    @Column(name = "regex")
    private String regex;
    // 数据类型
    @Column(name = "type", columnDefinition = "ENUM('NUMBER','BOOLEAN','STRING','JSON','OBJECT')")
    private String type;
    // 是否为数组
    @Column(name = "array", nullable = false)
    private boolean array;
    // 字段加密类型
    @Column(name = "encrypt", columnDefinition = "ENUM('CARD','MOBILE','CID','NAME')")
    @Convert(converter = HeliosDataJpaConverters.EncryptTypeConverter.class)
    private EncryptType encrypt;
    // 创建者
    @Column(name = "created_by")
    private Integer createdBy;
    // 创建时间
    @Column(name = "created_at")
    private Date createTime;
    // 修改时间
    @Column(name = "updated_at")
    private Date updateTime;

    public String getField() {
        return field;
    }

    public Field setField(String field) {
        this.field = field;
        return this;
    }

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Field setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Field setMinLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public Field setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public String getType() {
        return type;
    }

    public Field setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isArray() {
        return array;
    }

    public Field setArray(boolean array) {
        this.array = array;
        return this;
    }

    public EncryptType getEncrypt() {
        return encrypt;
    }

    public Field setEncrypt(EncryptType encrypt) {
        this.encrypt = encrypt;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Field setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Field setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Field setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
