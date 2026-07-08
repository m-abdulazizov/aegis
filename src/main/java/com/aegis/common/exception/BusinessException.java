package com.aegis.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private final ErrorCode code;
    private final HttpStatus status;

    public BusinessException(ErrorCode code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public static BusinessException badRequest(String message) {
        return new BusinessException(
                ErrorCode.BAD_REQUEST,
                message,
                HttpStatus.BAD_REQUEST
        );
    }

    public static BusinessException notFound(String message) {
        return new BusinessException(
                ErrorCode.RESOURCE_NOT_FOUND,
                message,
                HttpStatus.NOT_FOUND
        );
    }

    public static BusinessException conflict(String message) {
        return new BusinessException(
                ErrorCode.CONFLICT,
                message,
                HttpStatus.CONFLICT
        );
    }

    public ErrorCode getCode(){
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
