package com.aegis.batch.payload;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record MedicineBatchCreateRequest(

        @NotBlank(message = "Batch number is required")
        @Size(max = 100, message = "Batch number must not exceed 100 characters")
        String batchNumber,

        @NotNull(message = "Medicine id is required")
        UUID medicineId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Long quantity,

        @NotNull(message = "Manufacture date is required")
        LocalDate manufactureDate,

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        LocalDate expiryDate
) {
}