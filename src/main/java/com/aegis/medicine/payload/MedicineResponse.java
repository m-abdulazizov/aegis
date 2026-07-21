package com.aegis.medicine.payload;

import com.aegis.medicine.entity.MedicineStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MedicineResponse(
        UUID id,
        String name,
        String genericName,
        String dosage,
        String dosageForm,
        String registrationNumber,
        UUID manufacturerId,
        String manufacturerName,
        MedicineStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}