package com.gaurav.servicehub.servicehub.review.dto;

import java.util.UUID;

public record ReviewResponse(

        UUID id,

        UUID bookingId,

        UUID customerId,

        UUID providerId,

        UUID serviceId,

        Integer rating,

        String comment

) {
}