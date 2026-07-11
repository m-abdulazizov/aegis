package com.aegis.organization.service;

import com.aegis.common.exception.BusinessException;
import com.aegis.organization.entity.Organization;
import com.aegis.organization.entity.OrganizationStatus;
import com.aegis.organization.payload.OrganizationCreateRequest;
import com.aegis.organization.payload.OrganizationResponse;
import com.aegis.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService
{
    private final OrganizationRepository organizationRepository;

    @Transactional
    public OrganizationResponse create(OrganizationCreateRequest request) {
        validateUniqueFields(request);

        Organization organization = Organization.builder()
                .name(request.name().trim())
                .registrationNumber(request.registrationNumber().trim())
                .licenseNumber(trimToNull(request.licenseNumber()))
                .type(request.type())
                .status(OrganizationStatus.ACTIVE)
                .walletAddress(trimToNull(request.walletAddress()))
                .build();

        Organization saved = organizationRepository.save(organization);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrganizationResponse getById(UUID id) {
        Organization organization = getActiveEntityById(id);
        return toResponse(organization);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationResponse> getAll(Pageable pageable) {
        return organizationRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Organization getActiveEntityById(UUID id) {
        return organizationRepository.findByIdAndStatusNot(id, OrganizationStatus.BLOCKED)
                .orElseThrow(() -> BusinessException.notFound("Organization not found"));
    }

    private void validateUniqueFields(OrganizationCreateRequest request) {
        if (organizationRepository.existsByRegistrationNumberIgnoreCase(request.registrationNumber().trim())) {
            throw BusinessException.conflict("Organization with this registration number already exists");
        }

        String licenseNumber = trimToNull(request.licenseNumber());

        if (licenseNumber != null && organizationRepository.existsByLicenseNumberIgnoreCase(licenseNumber)) {
            throw BusinessException.conflict("Organization with this license number already exists");
        }
    }

    private OrganizationResponse toResponse(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getRegistrationNumber(),
                organization.getLicenseNumber(),
                organization.getType(),
                organization.getStatus(),
                organization.getWalletAddress(),
                organization.getCreatedAt(),
                organization.getUpdatedAt()
        );
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}