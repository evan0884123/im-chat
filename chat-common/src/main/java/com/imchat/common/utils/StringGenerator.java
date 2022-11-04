package com.imchat.common.utils;

import java.util.Random;

/**
 * 字符串生成器
 */
public final class StringGenerator {

    /**
     * 生成随即密码
     *
     * @param length 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandom(int length) {
        final int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9'};

        StringBuilder pwd = new StringBuilder();
        Random r = new Random();
        while (count < length) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        return pwd.toString();
    }

}
