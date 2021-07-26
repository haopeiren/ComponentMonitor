package com.ffcs.exception;

import com.ffcs.enums.ErrorCode;
import com.ffcs.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 参数缺失异常
     *
     * @param e INVALID_PARAM
     * @return 参数校验失败
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse servletRequestBindingException(ServletRequestBindingException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_PARAM);
    }

    /**
     * BindException
     *
     * @param e INVALID_PARAM
     * @return 参数校验失败
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse bindException(BindException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_PARAM);
    }

    /**
     * ConstraintViolationException
     *
     * @param e INVALID_PARAM
     * @return 参数校验失败
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_PARAM);
    }

    /**
     * MethodArgumentNotValidException
     *
     * @param e INVALID_PARAM
     * @return 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(ErrorCode.INVALID_PARAM);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse serviceException(ServiceException serviceException) {
        log.error("service exception, code : {}, message : {}",
                serviceException.getCode(), serviceException.getMessage());
        return new ErrorResponse(serviceException);
    }

    /**
     * 数据库访问异常
     *
     * @param dataAccessException DataAccessException
     * @return 错误响应体
     */
    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse dataAccessException(DataAccessException dataAccessException) {
        log.error("dataAccessException", dataAccessException);
        return new ErrorResponse(ErrorCode.DATA_ACCESS_EXCEPTION);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse forbiddenException(ForbiddenException forbiddenException) {
        log.error("forbidden exception");
        return new ErrorResponse(forbiddenException);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(Exception exception) {
        log.error("unknown error", exception);
        return new ErrorResponse(CommonConstants.UNKNOWN_ERROR, "unknown exception");
    }
}
