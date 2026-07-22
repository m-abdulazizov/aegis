package com.aegis.verification.service;

import com.aegis.batch.entity.MedicineBatch;
import com.aegis.batch.entity.MedicineBatchStatus;
import com.aegis.batch.service.MedicineBatchService;
import com.aegis.common.exception.BusinessException;
import com.aegis.organization.entity.Organization;
import com.aegis.organization.entity.OrganizationType;
import com.aegis.organization.service.OrganizationService;
import com.aegis.verification.entity.BatchVerificationRequest;
import com.aegis.verification.entity.BatchVerificationStatus;
import com.aegis.verification.payload.BatchVerificationResponse;
import com.aegis.verification.payload.BatchVerificationReviewRequest;
import com.aegis.verification.payload.BatchVerificationSubmitRequest;
import com.aegis.verification.repository.BatchVerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchVerificationService {

    private final BatchVerificationRequestRepository verificationRequestRepository;
    private final MedicineBatchService medicineBatchService;
    private final OrganizationService organizationService;

    @Transactional
    public BatchVerificationResponse submit(UUID batchId, BatchVerificationSubmitRequest request) {
        MedicineBatch batch = medicineBatchService.getActiveEntityById(batchId);

        validateBatchCanBeSubmitted(batch);

        if (verificationRequestRepository.existsByBatchIdAndStatus(batchId, BatchVerificationStatus.PENDING)) {
            throw BusinessException.conflict("This batch already has a pending verification request");
        }

        batch.setStatus(MedicineBatchStatus.PENDING_VERIFICATION);

        BatchVerificationRequest verificationRequest = BatchVerificationRequest.builder()
                .batch(batch)
                .status(BatchVerificationStatus.PENDING)
                .submittedBy(batch.getCurrentOwner())
                .submitNotes(trimToNull(request.notes()))
                .submittedAt(LocalDateTime.now())
                .build();

        BatchVerificationRequest saved = verificationRequestRepository.save(verificationRequest);

        return toResponse(saved);
    }

    @Transactional
    public BatchVerificationResponse approve(UUID requestId, BatchVerificationReviewRequest request) {
        BatchVerificationRequest verificationRequest = getPendingRequest(requestId);
        MedicineBatch batch = verificationRequest.getBatch();

        Organization reviewer = organizationService.getActiveEntityById(request.reviewerOrganizationId());
        validateRegulator(reviewer);

        validateBatchPendingVerification(batch);

        batch.setStatus(MedicineBatchStatus.VERIFIED);

        verificationRequest.setStatus(BatchVerificationStatus.APPROVED);
        verificationRequest.setReviewedBy(reviewer);
        verificationRequest.setReviewNotes(trimToNull(request.notes()));
        verificationRequest.setReviewedAt(LocalDateTime.now());

        return toResponse(verificationRequest);
    }

    @Transactional
    public BatchVerificationResponse reject(UUID requestId, BatchVerificationReviewRequest request) {
        BatchVerificationRequest verificationRequest = getPendingRequest(requestId);
        MedicineBatch batch = verificationRequest.getBatch();

        Organization reviewer = organizationService.getActiveEntityById(request.reviewerOrganizationId());
        validateRegulator(reviewer);

        validateBatchPendingVerification(batch);

        batch.setStatus(MedicineBatchStatus.REJECTED);

        verificationRequest.setStatus(BatchVerificationStatus.REJECTED);
        verificationRequest.setReviewedBy(reviewer);
        verificationRequest.setReviewNotes(trimToNull(request.notes()));
        verificationRequest.setReviewedAt(LocalDateTime.now());

        return toResponse(verificationRequest);
    }

    @Transactional(readOnly = true)
    public BatchVerificationResponse getById(UUID requestId) {
        BatchVerificationRequest verificationRequest = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> BusinessException.notFound("Verification request not found"));

        return toResponse(verificationRequest);
    }

    private BatchVerificationRequest getPendingRequest(UUID requestId) {
        return verificationRequestRepository.findByIdAndStatus(requestId, BatchVerificationStatus.PENDING)
                .orElseThrow(() -> BusinessException.notFound("Pending verification request not found"));
    }

    private void validateBatchCanBeSubmitted(MedicineBatch batch) {
        if (batch.getStatus() != MedicineBatchStatus.CREATED) {
            throw BusinessException.badRequest("Only CREATED batches can be submitted for verification");
        }
    }

    private void validateBatchPendingVerification(MedicineBatch batch) {
        if (batch.getStatus() != MedicineBatchStatus.PENDING_VERIFICATION) {
            throw BusinessException.badRequest("Batch is not pending verification");
        }
    }

    private void validateRegulator(Organization organization) {
        if (organization.getType() != OrganizationType.REGULATOR) {
            throw BusinessException.badRequest("Only regulator organizations can review batch verification requests");
        }
    }

    private BatchVerificationResponse toResponse(BatchVerificationRequest request) {
        Organization reviewedBy = request.getReviewedBy();

        return new BatchVerificationResponse(
                request.getId(),
                request.getBatch().getId(),
                request.getBatch().getBatchNumber(),
                request.getBatch().getStatus(),
                request.getStatus(),
                request.getSubmittedBy().getId(),
                request.getSubmittedBy().getName(),
                reviewedBy == null ? null : reviewedBy.getId(),
                reviewedBy == null ? null : reviewedBy.getName(),
                request.getSubmitNotes(),
                request.getReviewNotes(),
                request.getSubmittedAt(),
                request.getReviewedAt(),
                request.getCreatedAt(),
                request.getUpdatedAt()
        );
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}