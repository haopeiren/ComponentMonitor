package com.ffcs.exception;

import com.ffcs.enums.ErrorCode;

/**
 * error response
 **/
public class ErrorResponse {
    private int code;

    private String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ServiceException serviceException) {
        this(serviceException.getCode(), serviceException.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
