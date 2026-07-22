package com.aegis.verification.payload;

import com.aegis.batch.entity.MedicineBatchStatus;
import com.aegis.verification.entity.BatchVerificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BatchVerificationResponse(
        UUID id,
        UUID batchId,
        String batchNumber,
        MedicineBatchStatus batchStatus,
        BatchVerificationStatus verificationStatus,
        UUID submittedById,
        String submittedByName,
        UUID reviewedById,
        String reviewedByName,
        String submitNotes,
        String reviewNotes,
        LocalDateTime submittedAt,
        LocalDateTime reviewedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{
}
