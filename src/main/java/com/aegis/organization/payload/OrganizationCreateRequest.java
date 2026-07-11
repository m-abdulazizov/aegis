package com.aegis.organization.payload;

import com.aegis.organization.entity.OrganizationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrganizationCreateRequest(

        @NotBlank(message = "Organization name is required")
        @Size(max = 255, message = "Organization name must not exceed 255 characters")
        String name,

        @NotBlank(message = "Registration number is required")
        @Size(max = 100, message = "Registration number must not exceed 100 characters")
        String registrationNumber,

        @Size(max = 100, message = "License number must not exceed 100 characters")
        String licenseNumber,

        @NotNull(message = "Organization type is required")
        OrganizationType type,

        @Size(max = 255, message = "Wallet address must not exceed 255 characters")
        String walletAddress
) {}
