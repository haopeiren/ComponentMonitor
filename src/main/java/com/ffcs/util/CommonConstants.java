package com.ffcs.util;

import org.apache.poi.ss.formula.functions.T;

/**
 * constants
 */
public interface CommonConstants {
    /**
     * success code
     */
    int SUCCESS_CODE = 0;

    /**
     * 未知错误
     */
    int UNKNOWN_ERROR = -1;

    /**
     * success message
     */
    String SUCCESS_MESSAGE = "success";

    String DB_MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    String DB_MYSQL_URL = "jdbc:mysql://{IP}:{PORT}/{DATABASE}?useUnicode=true&characterEncoding=utf8&useSSL=false";
}
