package com.aegis.verification.controller;

import com.aegis.common.response.ApiResponse;
import com.aegis.verification.payload.BatchVerificationResponse;
import com.aegis.verification.payload.BatchVerificationReviewRequest;
import com.aegis.verification.payload.BatchVerificationSubmitRequest;
import com.aegis.verification.service.BatchVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BatchVerificationController {

    private final BatchVerificationService batchVerificationService;

    @PostMapping("/medicine-batches/{batchId}/verification-requests")
    public ApiResponse<BatchVerificationResponse> submit(
            @PathVariable UUID batchId,
            @Valid @RequestBody BatchVerificationSubmitRequest request
    ) {
        BatchVerificationResponse response = batchVerificationService.submit(batchId, request);
        return ApiResponse.success("Batch submitted for verification", response);
    }

    @PostMapping("/batch-verification-requests/{requestId}/approve")
    public ApiResponse<BatchVerificationResponse> approve(
            @PathVariable UUID requestId,
            @Valid @RequestBody BatchVerificationReviewRequest request
    ) {
        BatchVerificationResponse response = batchVerificationService.approve(requestId, request);
        return ApiResponse.success("Batch verification approved", response);
    }

    @PostMapping("/batch-verification-requests/{requestId}/reject")
    public ApiResponse<BatchVerificationResponse> reject(
            @PathVariable UUID requestId,
            @Valid @RequestBody BatchVerificationReviewRequest request
    ) {
        BatchVerificationResponse response = batchVerificationService.reject(requestId, request);
        return ApiResponse.success("Batch verification rejected", response);
    }

    @GetMapping("/batch-verification-requests/{requestId}")
    public ApiResponse<BatchVerificationResponse> getById(@PathVariable UUID requestId) {
        BatchVerificationResponse response = batchVerificationService.getById(requestId);
        return ApiResponse.success(response);
    }
}