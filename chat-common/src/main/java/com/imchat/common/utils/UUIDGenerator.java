package com.imchat.common.utils;

import java.util.UUID;

/**
 * uuid生成器
 */
public final class UUIDGenerator {

    /**
     * 生成UUID
     *
     * @return 生成uuid
     */
    public static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
