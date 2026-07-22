package com.aegis.verification.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BatchVerificationReviewRequest (

        @NotNull(message = "Reviewer organization ID is required")
        UUID reviewerOrganizationId,

        @Size(max = 2000, message = "Review notes must not exceed 2000 characters")
        String notes
)
{
}
