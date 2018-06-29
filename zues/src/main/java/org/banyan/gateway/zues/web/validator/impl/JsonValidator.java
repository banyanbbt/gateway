package org.banyan.gateway.zues.web.validator.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
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
 * @desc json validator
 * @date 2018-01-17 16:38:44
 */
public class JsonValidator implements Validator {

    private static Logger LOGGER = LoggerFactory.getLogger(JsonValidator.class);

    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        if (null != jsonNode && jsonNode.isTextual()) {
            String str = jsonNode.asText();

            if (StringUtil.isBlank(str)) {
                return false;
            }

            int len = str.length();
            if (null != max && Constants.LIMITLESS_LENGTH != max && max < len) {
                LOGGER.info("字段：{}长度最大值不得大于{}，当前字符串长度：{}", key, max, len);
                return false;
            }
            if (null != max && Constants.LIMITLESS_LENGTH != min && min > len) {
                LOGGER.info("字段：{}长度最小值不得小于{}，当前字符串长度：{}", key, min, len);
                return false;
            }
            try {
                JSON.parseObject(str);
                return true;
            } catch (JSONException e) {
                LOGGER.info("字段: {}不是标准json字符串, 当前值为: {}", key, str);
                return false;
            }
        } else {
            return false;
        }
    }
}
