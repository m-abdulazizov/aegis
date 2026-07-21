package com.aegis.medicine.controller;

import com.aegis.common.response.ApiResponse;
import com.aegis.medicine.payload.MedicineCreateRequest;
import com.aegis.medicine.payload.MedicineResponse;
import com.aegis.medicine.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ApiResponse<MedicineResponse> create(
            @Valid @RequestBody MedicineCreateRequest request
    ) {
        MedicineResponse response = medicineService.create(request);
        return ApiResponse.success("Medicine created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicineResponse> getById(@PathVariable UUID id) {
        MedicineResponse response = medicineService.getById(id);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<Page<MedicineResponse>> getAll(Pageable pageable) {
        Page<MedicineResponse> response = medicineService.getAll(pageable);
        return ApiResponse.success(response);
    }
}