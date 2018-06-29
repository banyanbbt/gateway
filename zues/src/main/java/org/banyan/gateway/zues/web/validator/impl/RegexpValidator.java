package org.banyan.gateway.zues.web.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.banyan.gateway.helios.util.StringUtil;
import org.banyan.gateway.zues.web.validator.Validator;

import java.util.regex.Pattern;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 正则验证
 * @date 2018-01-15 10:57:56
 */
public class RegexpValidator implements Validator {

    @Override
    public boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex) {
        if (null == jsonNode) {
            return false;
        }

        // 正则匹配
        boolean result = true;
        if (StringUtil.isNotBlank(regex)) {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            result = pattern.matcher(jsonNode.asText().trim()).matches();
        }

        return result;
    }
}
