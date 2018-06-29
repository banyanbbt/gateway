package org.banyan.gateway.helios.data.jpa.service.common;

import org.banyan.gateway.helios.data.jpa.domain.common.Config;
import org.banyan.gateway.helios.data.jpa.repository.common.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ConfigService
 * 配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年04月23日 15:21:00
 */
@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    /**
     * 获取Config并转换成Map
     * @return config -> value
     */
    public Map<String, String> configMap() {
        Map<String, String> configMap = new HashMap<>();

        Iterable<Config> configs = configRepository.findAll();
        if (null != configs) {
            configMap = StreamSupport.stream(configs.spliterator(), false)
                    .filter(config -> Objects.nonNull(config.getValue()) && Objects.nonNull(config.getConfig()))
                    .collect(Collectors.toMap(Config::getConfig, Config::getValue));
        }
        return configMap;
    }
}
