package com.aegis.organization.payload;

import com.aegis.organization.entity.OrganizationStatus;
import com.aegis.organization.entity.OrganizationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrganizationResponse(
        UUID id,
        String name,
        String registrationNumber,
        String licenseNumber,
        OrganizationType type,
        OrganizationStatus status,
        String walletAddress,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
