package org.banyan.gateway.helios.proto.dto.product;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.banyan.gateway.helios.common.ConfigKey;
import org.banyan.gateway.helios.common.Constants;
import org.banyan.gateway.helios.common.Interface;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * ProductConfig
 * 产品配置
 *
 * @author Kevin Huang
 * @since version
 * 2018年03月07日 11:10:00
 */
public class ProductConfig implements Serializable {
    private static final long serialVersionUID = -7311506541724017481L;

    private Map<ConfigKey, String> configs = new HashMap<>();

    public boolean isEmpty() {
        return configs.isEmpty();
    }

    public Set<ConfigKey> getKeys() {
        return configs.keySet();
    }

    public String set(ConfigKey configKey, String value) {
        return configs.put(configKey, value);
    }

    public String setCollection(ConfigKey configKey, Collection<String> values) {
        return configs.put(configKey, Joiner.on(Constants.COMMA).join(values));
    }

    public String get(ConfigKey configKey) {
        return configs.get(configKey);
    }

    public List<String> getCollection(ConfigKey configKey) {
        String value = configs.get(configKey);
        if (null != value && value.trim().length() > 0) {
            return Lists.newArrayList(value.split(Constants.COMMA));
        }
        return null;
    }

    public List<Interface> getInterfaces(ConfigKey configKey) {
        if (ConfigKey.ROUTING_KEY.equals(configKey)) {
            String value = configs.get(configKey);
            if (value != null) {
                return Arrays.stream(value.split(Constants.COMMA)).map(Interface::getByIFace).filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }
        return null;
    }


    public String remove(ConfigKey configKey) {
        return configs.remove(configKey);
    }

    public Map<ConfigKey, String> getConfigs() {
        return configs;
    }

    public ProductConfig setConfigs(Map<ConfigKey, String> configs) {
        this.configs = configs;
        return this;
    }
}
