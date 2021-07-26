package com.ffcs.util;

import java.util.UUID;

/**
 * id工具
 **/
public class IdUtil {
    /**
     * 随机生成uuid
     *
     * @return uuid
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
