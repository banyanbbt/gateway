package org.banyan.gateway.zues.web.validator;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 验证器接口
 * @date 2018-01-15 10:55:02
 */
public interface Validator {

    /**
     * 验证参数是否符合规则
     * @param key
     * @param jsonNode
     * @param min
     * @param max
     * @param regex
     * @return
     */
    boolean valid(String key, JsonNode jsonNode, Integer min, Integer max, String regex);
}
