package org.banyan.gateway.zues.web.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.zues.web.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 数字验证
 * @date 2018-01-15 10:57:56
 */
public class NumberValidator implements Validator {

    private static Logger LOGGER = LoggerFactory.getLogger(NumberValidator.class);
    private RegexpValidator regexpValidator;

    public NumberValidator(RegexpValidator regexpValidator) {
        super();
        this.regexpValidator = regexpValidator;
    }

    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        boolean result = false;
        if (null != jsonNode && jsonNode.isNumber()) {
            Number number = jsonNode.numberValue();
            if (null != max && Constants.LIMITLESS_LENGTH != max && max < number.longValue()) {
                LOGGER.info("字段：{}最大值不得大于{}，当前值：{}", key, max, number);
                return result;
            }
            if (null != max && Constants.LIMITLESS_LENGTH != min && min > number.longValue()) {
                LOGGER.info("字段：{}最小值不得小于{}，当前值：{}", key, min, number);
                return result;
            }

            // 正则匹配
            result = this.regexpValidator.valid(key, jsonNode, min, max, regex);
        }
        return result;
    }

}
