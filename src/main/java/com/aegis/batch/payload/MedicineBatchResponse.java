package com.aegis.batch.payload;

import com.aegis.batch.entity.MedicineBatchStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MedicineBatchResponse(
        UUID id,
        String batchNumber,
        UUID medicineId,
        String medicineName,
        String dosage,
        String dosageForm,
        Long quantity,
        LocalDate manufactureDate,
        LocalDate expiryDate,
        MedicineBatchStatus status,
        UUID currentOwnerId,
        String currentOwnerName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}