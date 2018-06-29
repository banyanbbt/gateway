package org.banyan.gateway.helios.data.jpa.service.account;

import org.banyan.gateway.helios.data.jpa.domain.account.AccountKey;
import org.banyan.gateway.helios.data.jpa.domain.account.Encryption;
import org.banyan.gateway.helios.data.jpa.dto.AccountKeyDto;
import org.banyan.gateway.helios.data.jpa.repository.account.AccountKeyRepository;
import org.banyan.gateway.helios.data.jpa.repository.account.EncryptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 账户公私钥 service
 * @date 2018-01-12 14:58:03
 */
@Service
public class AccountKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountKeyService.class);

    @Autowired
    private AccountKeyRepository accountKeyRepository;
    @Autowired
    private EncryptionRepository encryptionRepository;

    /**
     * 根据账号查询有效期内且可用密钥对
     * @param account
     * @return
     */
    public AccountKeyDto findByAccount(String account) {
        AccountKeyDto accountKeyDto = null;

        List<AccountKey> accountKeys = this.accountKeyRepository.findByAccountAndAvailable(account, true);
        if (null == accountKeys || accountKeys.isEmpty()) {
            LOGGER.info("账号{}未配置对应的可用的密钥对信息", account);
            // 如果没有查询到，直接返回null
            return null;
        }

        AccountKey key = null;
        // 查询到密钥信息，判断是否在有效期内
        for (AccountKey accountKey: accountKeys) {
            Long currentTime = System.currentTimeMillis();
            Date effectiveTime = accountKey.getEffectiveAt();
            Date expireTime = accountKey.getExpireAt();

            if ((null == effectiveTime || currentTime >= effectiveTime.getTime()) && (null == expireTime || currentTime < expireTime.getTime())) {
                if (null == key) {
                    key = accountKey;
                } else {
                    if (accountKey.getId() > key.getId()) {
                        key = accountKey;
                    }
                }
            }
        }

        if (null != key) {
           Encryption encryption = encryptionRepository.findOne(key.getEncryptionId());
           if (null != encryption) {
               accountKeyDto = new AccountKeyDto(key, encryption);
           }
        }

        LOGGER.info("账号{}对应的account_key表中最新记录id: {}", account, null == accountKeyDto ? 0 : accountKeyDto.getId());
        return accountKeyDto;
    }
}
