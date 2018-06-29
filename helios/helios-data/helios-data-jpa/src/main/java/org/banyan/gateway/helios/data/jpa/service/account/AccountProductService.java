package org.banyan.gateway.helios.data.jpa.service.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountProduct;
import org.banyan.gateway.helios.data.jpa.repository.account.AccountProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户产品 service
 * @date 2018-01-12 14:58:03
 */
@Service
public class AccountProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountProductService.class);

    @Autowired
    private AccountProductRepository accountProductRepository;

    /**
     * 根据账号和产品号查询有效期内且可用的产品
     * @param account
     * @param product
     * @return
     */
    public AccountProduct findByAccountAndProduct(String account, String product) {
        AccountProduct accountProduct = this.accountProductRepository.findByAccountAndProductAndStatus(account, product, true);
        if (null == accountProduct) {
            LOGGER.info("账号{}未配置产品{}", account, product);
            // 如果没有查询到，直接返回null
            return null;
        }

        // 查询到账户对应的产品信息，判断是否在有效期内
        Long currentTime = System.currentTimeMillis();
        Date effectiveTime = accountProduct.getEffectiveAt();
        Date expireTime = accountProduct.getExpireAt();
        if ((null == effectiveTime || currentTime >= effectiveTime.getTime()) && (null == expireTime || currentTime < expireTime.getTime())) {
            return accountProduct;
        }
        LOGGER.info("账号{}和产品{}对应的account_product表中记录未在有效时间内", account, product);
        return null;
    }

    /**
     * 增加次数
     * @param account
     * @param product
     */
    @Transactional
    public void increaseUsedCount(String account, String product) {
        accountProductRepository.increaseUsedCount(account, product);
    }
}
