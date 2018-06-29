package org.banyan.gateway.helios.util;

import java.util.List;

/**
 * Copyright (C), 2017, Banyan Network Foundation
 *
 * @author levi
 * @version 0.0.1
 * @desc 字符串工具类
 * @date 2017-12-25 09:31:48
 */
public class StringUtil {

    private static final int INDEX_NOT_FOUND = -1;
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtil.isBlank(str);
    }

    public static byte[] strToByte(String input) {
        if (null == input || input.length() < 1) {
            return null;
        }

        int len = input.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len / 2; i++) {
            int high = Integer.parseInt(input.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(input.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String byteToStr(byte[] input) {
        if (null == input) {
            return null;
        }

        int len = input.length;
        char myChar[] = new char[len * 2];
        int k = 0;
        for (byte b : input) {
            myChar[k++] = HEX_DIGITS[b >>> 4 & 0x0f];
            myChar[k++] = HEX_DIGITS[b & 0x0f];
        }
        return new String(myChar);
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }


    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static int countMatches(String str, String sub) {
        if (!hasLength(str) || !hasLength(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * 判断string list是否全为空
     * @param strings
     * @return
     */
    public static boolean isAllBlack(List<String> strings) {
        boolean match = strings.stream().anyMatch(StringUtil::isNotBlank);
        return !match;
    }
}
