package org.banyan.gateway.helios.data.jpa.dto;

import org.banyan.gateway.helios.data.jpa.model.EncryptType;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc product field dto
 * @date 2018-01-16 16:57:50
 */
public class ProductFieldDto implements Serializable {
    private static final long serialVersionUID = -1909728918768750037L;

    private Integer id;
    // 参数key
    private String field;
    // 参数名称
    private String name;
    // 父参数key
    private Integer parentId;
    // 是否必填
    private boolean required;
    // 字符串最小长度，数字最小值
    private Integer minLength;
    // 字符串最大长度，数字最大值
    private Integer maxLength;
    // 是否为数组
    private boolean array;
    // 数据类型
    private String type;
    // 正则表达式
    private String regex;
    // 字段加密类型
    private EncryptType encryptType;
    // 子field
    private List<ProductFieldDto> children;

    public ProductFieldDto() {
    }

    public ProductFieldDto(Integer id, String field, String name, Integer parentId, boolean required, Integer minLength, Integer maxLength, boolean array, String type, String regex, EncryptType encryptType) {
        this.id = id;
        this.field = field;
        this.name = name;
        this.parentId = parentId;
        this.required = required;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.array = array;
        this.type = type;
        this.regex = regex;
        this.encryptType = encryptType;
    }

    public ProductFieldDto(Integer id, String field, String name, Integer parentId, boolean required, Integer minLength, Integer maxLength, boolean array, String type, String regex, EncryptType encryptType, List<ProductFieldDto> children) {
        this(id, field, name, parentId, required, minLength, maxLength, array, type, regex, encryptType);
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public ProductFieldDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getField() {
        return field;
    }

    public ProductFieldDto setField(String field) {
        this.field = field;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductFieldDto setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public Integer getParentId() {
        return parentId;
    }

    public ProductFieldDto setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public ProductFieldDto setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public ProductFieldDto setMinLength(Integer minLength) {
        this.minLength = minLength;
        return this;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public ProductFieldDto setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public boolean isArray() {
        return array;
    }

    public ProductFieldDto setArray(boolean array) {
        this.array = array;
        return this;
    }

    public String getType() {
        return type;
    }

    public ProductFieldDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public ProductFieldDto setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public EncryptType getEncryptType() {
        return encryptType;
    }

    public ProductFieldDto setEncryptType(EncryptType encryptType) {
        this.encryptType = encryptType;
        return this;
    }

    public List<ProductFieldDto> getChildren() {
        return children;
    }

    public ProductFieldDto setChildren(List<ProductFieldDto> children) {
        this.children = children;
        return this;
    }
}
