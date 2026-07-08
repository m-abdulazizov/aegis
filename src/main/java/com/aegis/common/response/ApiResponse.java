package com.aegis.common.response;

import java.time.LocalDateTime;

public record ApiResponse<T> (
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
)
{
    public static <T> ApiResponse<T> success(String message, T data)
    {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> success(T data){
        return success("Success", data);
    }

    public static ApiResponse<Void> success(String message)
    {
        return new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
    }
}
