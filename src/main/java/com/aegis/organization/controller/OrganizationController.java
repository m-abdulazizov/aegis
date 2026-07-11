package com.aegis.organization.controller;

import com.aegis.common.response.ApiResponse;
import com.aegis.organization.payload.OrganizationCreateRequest;
import com.aegis.organization.payload.OrganizationResponse;
import com.aegis.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ApiResponse<OrganizationResponse> create(
            @Valid @RequestBody OrganizationCreateRequest request
    ) {
        OrganizationResponse response = organizationService.create(request);
        return ApiResponse.success("Organization created successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationResponse> getById(@PathVariable UUID id) {
        OrganizationResponse response = organizationService.getById(id);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<Page<OrganizationResponse>> getAll(Pageable pageable) {
        Page<OrganizationResponse> response = organizationService.getAll(pageable);
        return ApiResponse.success(response);
    }
}