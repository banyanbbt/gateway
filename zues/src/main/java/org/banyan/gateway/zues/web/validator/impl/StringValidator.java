package org.banyan.gateway.zues.web.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.util.StringUtil;
import org.banyan.gateway.zues.web.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 字符串验证
 * @date 2018-01-15 10:57:56
 */
public class StringValidator implements Validator {

    private static Logger LOGGER = LoggerFactory.getLogger(StringValidator.class);
    private RegexpValidator regexpValidator;

    public StringValidator(RegexpValidator regexpValidator) {
        super();
        this.regexpValidator = regexpValidator;
    }
    
    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        boolean result = false;
        if (null != jsonNode && jsonNode.isTextual()) {
            String str = jsonNode.asText();

            if (StringUtil.isBlank(str)) {
                return result;
            }

            int len = str.length();
            if (null != max && Constants.LIMITLESS_LENGTH != max && max < len) {
                LOGGER.info("字段：{}长度最大值不得大于{}，当前字符串长度：{}", key, max, len);
                return result;
            }
            if (null != min && Constants.LIMITLESS_LENGTH != min && min > len) {
                LOGGER.info("字段：{}长度最小值不得小于{}，当前字符串长度：{}", key, min, len);
                return result;
            }

            // 正则匹配
            result = regexpValidator.valid(key, jsonNode, min, max, regex);
        }
        return result;
    }

}
