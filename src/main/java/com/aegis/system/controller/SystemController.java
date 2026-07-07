package com.aegis.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class SystemController
{
    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "application", "Aegis",
                "message", "Medicine batch verification and custody tracking platform",
                "status", "RUNNING",
                "timestamp", LocalDateTime.now()
        );
    }

    @GetMapping("/api/v1/system/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "aegis-api",
                "timestamp", LocalDateTime.now()
        );
    }
}
