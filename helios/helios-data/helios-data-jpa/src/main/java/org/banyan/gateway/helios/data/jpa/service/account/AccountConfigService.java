package org.banyan.gateway.helios.data.jpa.service.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountConfig;
import org.banyan.gateway.helios.data.jpa.domain.product.ProductConfig;
import org.banyan.gateway.helios.data.jpa.dto.ProductConfigDto;
import org.banyan.gateway.helios.data.jpa.repository.account.AccountConfigRepository;
import org.banyan.gateway.helios.data.jpa.repository.product.ProductConfigRepository;
import org.banyan.gateway.helios.data.jpa.service.common.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * AccountConfigService
 * 账号产品配置
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月06日 19:46:00
 */

@Service
public class AccountConfigService {
    @Autowired
    private AccountConfigRepository accountConfigRepository;
    @Autowired
    private ProductConfigRepository productConfigRepository;
    @Autowired
    private ConfigService configService;

    /**
     *
     * list.forEach(productConfig -> {
     *     String key = productConfig.getConfig();
     *     AccountConfig accountConfig = accountConfigMap.get(key);
     *     ConfigDto configDto = new ConfigDto(productConfig, accountConfig);
     *     if (null != configDto.getConfig()) {
     *         configs.add(configDto);
     *     }
     * });
     *
     * configs = list.parallelStream().collect(HashSet::new, (set, productConfig) -> {
     *     ConfigDto configDto = new ConfigDto(productConfig, accountConfigMap.get(productConfig.getConfig()));
     *     if (null != configDto.getConfig()) {
     *         set.add(configDto);
     *     }
     * }, HashSet::addAll);
     *
     * 根据账号和产品查找配置
     * @param account
     * @param product
     * @return
     */
    public Set<ProductConfigDto> findByAccountAndProduct(String account, String product) {
        Set<ProductConfigDto> configs = null;

        List<ProductConfig> list = productConfigRepository.findByProduct(product);

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, AccountConfig> accountConfigMap = findAccountConfigs(account, product);
            Map<String, String> configMap = this.configService.configMap();

            configs = list.stream().map(productConfig -> {
                ProductConfigDto productConfigDto = new ProductConfigDto(productConfig, accountConfigMap.get(productConfig.getConfig()));
                if (null == productConfigDto.getConfigValue()) {
                    productConfigDto.setConfigValue(configMap.get(productConfig.getConfig()));
                }
                return productConfigDto;
            }).filter(AccountConfigService.this::isNull).collect(Collectors.toSet());
        }

        return configs;
    }

    /**
     * 将账号和产品查找配置装换为Map
     * @param account
     * @param product
     * @return
     */
    public Map<String, AccountConfig> findAccountConfigs(String account, String product) {
        Map<String, AccountConfig> map = new HashMap<>();

        Set<AccountConfig> accountConfigs = accountConfigRepository.findByAccountAndProduct(account, product);
        if (!CollectionUtils.isEmpty(accountConfigs)) {
            map.putAll(accountConfigs.stream().collect(Collectors.toMap(AccountConfig::getConfig, x -> x)));
        }
        return map;
    }

    private boolean isNull(ProductConfigDto productConfigDto) {
        boolean result = false;
        if (nonNull(productConfigDto) && nonNull(productConfigDto.getConfig()) && nonNull(productConfigDto.getConfigValue())) {
            result = true;
        }
        return result;
    }
}
