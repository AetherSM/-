package com.example.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */
public class MD5Util {

    /**
     * MD5加密
     * @param plainText 明文
     * @return 加密后的32位小写字符串
     */
    public static String encode(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(plainText.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    result.append("0");
                }
                result.append(hex);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 验证密码
     * @param plainText 明文
     * @param encodedText 加密后的文本
     * @return 是否匹配
     */
    public static boolean verify(String plainText, String encodedText) {
        if (plainText == null || encodedText == null) {
            return false;
        }
        return encode(plainText).equals(encodedText);
    }
}

