package com.aegis.system.controller;

import com.aegis.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class SystemController {

    @GetMapping("/")
    public ApiResponse<SystemInfoResponse> root() {
        SystemInfoResponse response = new SystemInfoResponse(
                "Aegis",
                "Medicine batch verification and custody tracking platform",
                "RUNNING",
                LocalDateTime.now()
        );

        return ApiResponse.success("Application is running", response);
    }

    @GetMapping("/api/v1/system/health")
    public ApiResponse<SystemHealthResponse> health() {
        SystemHealthResponse response = new SystemHealthResponse(
                "UP",
                "aegis-api",
                LocalDateTime.now()
        );

        return ApiResponse.success("Service is healthy", response);
    }

    public record SystemInfoResponse(
            String application,
            String description,
            String status,
            LocalDateTime timestamp
    ) {
    }

    public record SystemHealthResponse(
            String status,
            String service,
            LocalDateTime timestamp
    ) {
    }
}