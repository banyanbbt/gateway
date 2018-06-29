package org.banyan.gateway.zues.web.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.banyan.gateway.zues.web.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc boolean 验证
 * @date 2018-01-15 10:57:56
 */
public class BooleanValidator implements Validator {

    private static Logger LOGGER = LoggerFactory.getLogger(BooleanValidator.class);

    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        boolean result = false;
        if (null != jsonNode && jsonNode.isBoolean()) {
            result = true;
        } else {
            LOGGER.info("字段：{} 不是布尔类型，当前值：{}", key, jsonNode);
        }
        return result;
    }
}
