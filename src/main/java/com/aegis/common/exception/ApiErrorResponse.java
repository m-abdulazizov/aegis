package com.aegis.common.exception;

import com.sun.jdi.connect.Connector;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        boolean success,
        String message,
        String code,
        int status,
        String path,
        Map<String, String> validationErrors,
        LocalDateTime timeStamp
) {
    public static ApiErrorResponse of(
            String message,
            String code,
            int status,
            String path
    ){
        return new ApiErrorResponse(
                false,
                message,
                code,
                status,
                path,
                null,
                LocalDateTime.now()
        );
    }

    public static ApiErrorResponse validation(
            String message,
            int status,
            String path,
            Map<String, String> validationErrors
    ){
        return new ApiErrorResponse(
                false,
                message,
                ErrorCode.VALIDATION_FAILED.name(),
                status,
                path,
                validationErrors,
                LocalDateTime.now()
        );
    }
}
