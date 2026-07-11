package com.aegis.organization.repository;

import com.aegis.organization.entity.Organization;
import com.aegis.organization.entity.OrganizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID>
{
    Optional<Organization> findByIdAndStatusNot(UUID id, OrganizationStatus status);

    boolean existsByRegistrationNumberIgnoreCase(String registrationNumber);

    boolean existsByLicenseNumberIgnoreCase(String licenseNumber);
}
