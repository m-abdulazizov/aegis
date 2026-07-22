package com.aegis.verification.repository;

import com.aegis.verification.entity.BatchVerificationRequest;
import com.aegis.verification.entity.BatchVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BatchVerificationRequestRepository extends
        JpaRepository<BatchVerificationRequest, UUID>
{
    boolean existsByBatchIdAndStatus(UUID batchId, BatchVerificationStatus status);

    Optional<BatchVerificationRequest> findByIdAndStatus(UUID id, BatchVerificationStatus status);
}
