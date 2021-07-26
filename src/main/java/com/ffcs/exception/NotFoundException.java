package com.ffcs.exception;

import com.ffcs.enums.ErrorCode;

/**
 * NotFoundException
 **/
public class NotFoundException extends ServiceException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(int code, String message) {
        super(code, message);
    }
}
