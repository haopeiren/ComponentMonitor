package com.ffcs.enums;

/**
 * 错误码
 */
public enum ErrorCode {
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(-1, "unknown exception"),

    /**
     * 成功
     */
    SUCCESS(0, "success"),

    INVALID_PARAM(1, "invalid param"),

    /**
     * 无权限
     */
    FORBIDDEN(2, "you have no rights"),

    /**
     * 用户不存在
     */
    USER_NOT_EXISTS(3, "user is not exists"),

    /**
     * 读取文件失败
     */
    READ_FILE_EXCEPTION(4, "failed to read file"),

    DOWNLOAD_FILE_EXCEPTION(5, "download failed"),

    DATA_ACCESS_EXCEPTION(6, "failed to access database")
    ;

    private int code;

    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
