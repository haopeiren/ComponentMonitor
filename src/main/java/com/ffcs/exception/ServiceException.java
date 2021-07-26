package com.ffcs.exception;

import com.ffcs.enums.ErrorCode;

/**
 * 业务异常
 **/
public class ServiceException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
