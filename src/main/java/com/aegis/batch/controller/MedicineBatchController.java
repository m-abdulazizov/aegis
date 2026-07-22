package com.aegis.batch.controller;

import com.aegis.batch.payload.MedicineBatchCreateRequest;
import com.aegis.batch.payload.MedicineBatchResponse;
import com.aegis.batch.service.MedicineBatchService;
import com.aegis.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicine-batches")
public class MedicineBatchController {

    private final MedicineBatchService medicineBatchService;

    @PostMapping
    public ApiResponse<MedicineBatchResponse> create(
            @Valid @RequestBody MedicineBatchCreateRequest request
    ) {
        MedicineBatchResponse response = medicineBatchService.create(request);
        return ApiResponse.success("Medicine batch created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineBatchResponse> getById(@PathVariable UUID id) {
        MedicineBatchResponse response = medicineBatchService.getById(id);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<Page<MedicineBatchResponse>> getAll(Pageable pageable) {
        Page<MedicineBatchResponse> response = medicineBatchService.getAll(pageable);
        return ApiResponse.success(response);
    }
}