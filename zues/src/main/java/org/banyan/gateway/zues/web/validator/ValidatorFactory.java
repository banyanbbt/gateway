package org.banyan.gateway.zues.web.validator;

import org.banyan.gateway.zues.web.validator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 验证器工厂
 * @date 2018-01-15 10:56:05
 */
public class ValidatorFactory {
    private static Logger LOGGER = LoggerFactory.getLogger(ValidatorFactory.class);
    private static Map<ValidateType, Validator> validatorInstances = new HashMap<>();

    static {
        RegexpValidator regexpValidator = new RegexpValidator();
        validatorInstances.put(ValidateType.BOOLEAN, new BooleanValidator());
        validatorInstances.put(ValidateType.NUMBER, new NumberValidator(regexpValidator));
        validatorInstances.put(ValidateType.STRING, new StringValidator(regexpValidator));
        validatorInstances.put(ValidateType.JSON, new JsonValidator());
        validatorInstances.put(ValidateType.OBJECT, new ObjectValidator());
    }

    public enum ValidateType {
        NUMBER, // 数字
        BOOLEAN, // 布尔
        STRING, // 字符串
        JSON, // JSON
        OBJECT // 对象
    }

    /**
     * 获取指定类型的验证器
     * @param type
     * @return
     */
    public static Validator getValidator(ValidateType type) {
        return validatorInstances.get(type);
    }
}
