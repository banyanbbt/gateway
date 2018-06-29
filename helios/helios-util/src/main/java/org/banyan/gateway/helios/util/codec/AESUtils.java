package org.banyan.gateway.helios.util.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc aes加密工具类
 * @date 2017-12-25 09:31:48
 */
public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 执行加密
     *
     * @param content  待加密内容
     * @param iv       向量
     * @param password 密钥
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] content, byte[] iv, byte[] password) throws Exception {
        return encrypt(content, new IvParameterSpec(iv), generateKey(password));
    }

    /**
     * 执行加密
     *
     * @param content 待加密内容
     * @param key     密钥规范
     * @param iv      初始化向量
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] content, IvParameterSpec iv, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(content);
    }

    /**
     * 执行解密
     *
     * @param content  待加密内容
     * @param iv       向量
     * @param password 密钥
     * @return String 解密后明文
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] content, byte[] iv, byte[] password) throws Exception {
        return decrypt(content, new IvParameterSpec(iv), generateKey(password));
    }

    /**
     * 执行解密
     *
     * @param content 待加密内容
     * @param key     密钥规范
     * @param iv      初始化向量
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] content, IvParameterSpec iv, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(content);
    }

    /**
     * 将byte 转为 SecretKeySpec
     *
     * @param key byte
     * @return SecretKeySpec
     */
    public static SecretKeySpec generateKey(byte[] key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        return secretKeySpec;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);

    }
}
