package org.banyan.gateway.eris.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * <p>
 * 加解密工具类
 *
 * @author Norman Hu
 * @since 1.0.0
 * <p>
 * 2018/8/27 09:27
 */
public class CryptoUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

    public CryptoUtils() {
    }

    public static PublicKey getRSAPublicKey(byte[] keyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static PrivateKey getRSAPrivateKey(byte[] keyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static SecretKey createDESKey(byte[] keyBytes) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec(keyBytes);
            return keyFactory.generateSecret(keySpec);
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] rsaEncrypt(Key key, byte[] dataBytes) {
        return operate("RSA", 1, key, dataBytes);
    }

    public static byte[] rsaDecrypt(Key key, byte[] dataBytes) {
        return operate("RSA", 2, key, dataBytes);
    }

    public static byte[] desEncrypt(Key key, byte[] dataBytes) {
        return operate("DES", 1, key, dataBytes);
    }

    public static byte[] desDecrypt(Key key, byte[] dataBytes) {
        return operate("DES", 2, key, dataBytes);
    }

    private static byte[] operate(String transformation, int opmode, Key key, byte[] dataBytes) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(opmode, key);
            return cipher.doFinal(dataBytes);
        } catch (GeneralSecurityException var5) {
            LOGGER.info("解密异常");
            return null;
        }
    }

}
