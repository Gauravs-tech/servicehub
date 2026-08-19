package com.gaurav.servicehub.servicehub.provider.dto;

import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProviderResponse(

        UUID id,

        UUID userId,

        String firstName,

        String lastName,

        String bio,

        Integer experienceYears,

        ProviderStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}