package com.ffcs.exception;

import com.ffcs.enums.ErrorCode;

/**
 * ForbiddenException
 **/
public class ForbiddenException extends ServiceException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ForbiddenException(int code, String message) {
        super(code, message);
    }
}
