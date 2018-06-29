package org.banyan.gateway.zues.web.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.banyan.gateway.zues.web.validator.Validator;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 对象验证
 * @date 2018-01-15 10:57:56
 */
public class ObjectValidator implements Validator {

    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        boolean result = false;
        if (null != jsonNode && jsonNode.isObject()) {
            result = true;
        }
        return result;
    }

}
