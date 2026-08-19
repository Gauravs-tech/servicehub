package com.gaurav.servicehub.servicehub.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateServiceRequest(

        @NotBlank(message = "Service name is required")
        @Size(max = 100, message = "Service name cannot exceed 100 characters")
        String name,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(
                value = "0.01",
                message = "Price must be greater than 0"
        )
        BigDecimal price,

        @NotNull(message = "Estimated duration is required")
        @Min(
                value = 1,
                message = "Estimated duration must be at least 1 minute"
        )
        Integer estimatedDurationMinutes
) {
}