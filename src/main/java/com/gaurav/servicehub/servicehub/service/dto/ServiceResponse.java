package com.gaurav.servicehub.servicehub.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceResponse(

        UUID id,

        UUID providerId,

        String providerName,

        String name,

        String description,

        BigDecimal price,

        Integer estimatedDurationMinutes,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}