package com.aegis.verification.payload;

import jakarta.validation.constraints.Size;

public record BatchVerificationSubmitRequest(

        @Size(max = 2000, message = "Submit notes must not exceed 2000 characters")
        String notes
) {
}
