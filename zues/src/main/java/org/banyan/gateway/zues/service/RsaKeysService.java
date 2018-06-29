package org.banyan.gateway.zues.service;

import org.banyan.gateway.helios.data.jpa.dto.EncryptionDto;
import org.banyan.gateway.helios.util.codec.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * RsaKeysService 密钥管理
 *
 * @author Kevin Huang
 * @since version
 * <p>
 * 2018年03月02日 14:58:00
 */
@Service
public class RsaKeysService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateService.class);

    @Autowired
    private RSAUtil rsaUtil;

    // banyan 密钥
    private final ConcurrentMap<Integer, PrivateKey> encryptionMap = new ConcurrentHashMap<>();
    // 客户公钥
    private final ConcurrentMap<String, Map<String, PublicKey>> publicKeyMap = new ConcurrentHashMap<>();

    /**
     * 获取banyan私钥
     * @param encryptionDto
     * @return
     */
    public PrivateKey getBanyanPrivateKey(EncryptionDto encryptionDto) {
        PrivateKey privateKey = encryptionMap.get(encryptionDto.getId());
        if (null == privateKey) {
            synchronized(encryptionMap) {
                privateKey = encryptionMap.get(encryptionDto.getId());
                if (null == privateKey) {
                    privateKey = rsaUtil.restorePrivateKey(encryptionDto.getPrivateKey());
                    if (null != privateKey) {
                        encryptionMap.put(encryptionDto.getId(), privateKey);
                        LOGGER.info("添加banyan私钥到缓存，id: {}", encryptionDto.getId());
                    }
                }
            }
        }
        return privateKey;
    }


    /**
     * 获取客户公钥
     * @param account
     * @param publicKeyStr
     * @return
     */
    public PublicKey getCustomerPublicKey(String account, String publicKeyStr) {
        PublicKey publicKey = null;
        Map<String, PublicKey> accountKey = publicKeyMap.get(account);
        if (null == accountKey) {
            synchronized(publicKeyMap) {
                accountKey = publicKeyMap.get(account);
                if (null == accountKey) {
                    accountKey = new HashMap<>();
                    publicKeyMap.put(account, accountKey);
                }
            }
        }

        publicKey = accountKey.get(publicKeyStr);
        if (null == publicKey) {
            synchronized(publicKeyMap) {
                publicKey = accountKey.get(publicKeyStr);
                if (null == publicKey) {
                    // 清空旧的公钥
                    if (!CollectionUtils.isEmpty(accountKey)) {
                        accountKey.clear();
                        LOGGER.info("删除客户：{}缓存中的公钥", account);
                    }
                    publicKey = rsaUtil.restorePublicKey(publicKeyStr);
                    if (null != publicKey) {
                        accountKey.put(publicKeyStr, publicKey);
                        LOGGER.info("添加客户：{}公钥到缓存", account);
                    }
                }
            }
        }

        return publicKey;
    }
}
