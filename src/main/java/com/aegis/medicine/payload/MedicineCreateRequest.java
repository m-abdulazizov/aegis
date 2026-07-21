package com.aegis.medicine.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MedicineCreateRequest (
        @NotBlank(message = "Medicine name is required")
        @Size(max = 255, message = "Medicine name must not exceed 255 characters")
        String name,

        @Size(max = 255, message = "Generic name must not exceed 255 characters")
        String genericName,

        @NotBlank(message = "Dosage is required")
        @Size(max = 100, message = "Dosage must not exceed 100 characters")
        String dosage,

        @NotBlank(message = "Dosage form is required")
        @Size(max = 100, message = "Dosage form must not exceed 100 characters")
        String dosageForm,

        @NotBlank(message = "Registration number is required")
        @Size(max = 100, message = "Registration number must not exceed 100 characters")
        String registrationNumber,

        @NotNull(message = "Manufacturer id is required")
        UUID manufacturerId
)
{}
