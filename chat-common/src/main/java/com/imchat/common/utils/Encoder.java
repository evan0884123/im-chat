package com.imchat.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public final class Encoder {

    /**
     * 生成32位md5码
     *
     * @param password 密码
     * @param salt     加盐
     * @return 32位字符串
     */
    public static String md5Password(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("md5");
        byte[] result = digest.digest((password + salt).getBytes());
        StringBuilder buffer = new StringBuilder();
        for (byte b : result) {
            int number = b & 0xff;
            String str = Integer.toHexString(number);
            if (str.length() == 1) {
                buffer.append("0");
            }
            buffer.append(str);
        }

        return buffer.toString();
    }

}
