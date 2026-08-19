package com.gaurav.servicehub.servicehub.provider.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateProviderRequest(

        @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
        String bio,

        @Min(value = 0, message = "Experience years cannot be negative")
        @Max(value = 50, message = "Experience years cannot exceed 50")
        Integer experienceYears
) {
}