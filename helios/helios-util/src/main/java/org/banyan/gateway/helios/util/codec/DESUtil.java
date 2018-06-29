package org.banyan.gateway.helios.util.codec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc des加密工具类
 * @date 2017-12-25 09:31:48
 */
public class DESUtil {
    private static final String ALGORITHM = "DES";
    private static final String DES = "DES";
    private static final String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";

    private static final String CODE_VECTOR = "UP SMART";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static String encrypt(String key, String data) throws Exception {
        return encrypt(toKey(key), data.getBytes(DEFAULT_CHARSET));
    }

    public static String encryptWithDESMode(String key, String data) throws Exception {
        return encryptWithDESMode(toKey(key), data.getBytes(DEFAULT_CHARSET));
    }

    public static String decrypt(String key, String data) throws Exception {
        return decrypt(toKey(key), data.getBytes(DEFAULT_CHARSET));
    }

    public static String decryptWithDESMode(String key, String data) throws Exception {
        return decryptWithDESMode(toKey(key), data.getBytes(DEFAULT_CHARSET));
    }

    public static String encrypt(Key secretKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(CODE_VECTOR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] bytes = cipher.doFinal(data);
        return Base64.encodeBase64String(bytes);
    }

    public static String encryptWithDESMode(Key secretKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecureRandom sr = new SecureRandom();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        byte[] bytes = cipher.doFinal(data);
        return Base64.encodeBase64String(bytes);
    }

    public static String decrypt(Key secretKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(CODE_VECTOR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        return  new String(cipher.doFinal(Base64.decodeBase64(data)), DEFAULT_CHARSET);
    }

    public static String decryptWithDESMode(Key secretKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(DES);
        SecureRandom sr = new SecureRandom();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        return  new String(cipher.doFinal(Base64.decodeBase64(data)), DEFAULT_CHARSET);
    }

    public static Key toKey(String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec dks = new DESKeySpec(key.getBytes(DEFAULT_CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        Key secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    public static void main(String[] args) throws Exception {
        String e = DESUtil.encrypt("huangkunyhxhkkhasdakhsdhksajkldjla", "6222320003767655");
        System.out.println(e);
        String data = DESUtil.decrypt("huangkunyhxhkkhasdakhsdhksajkldjla", e);
        System.out.println(data);
    }
}
