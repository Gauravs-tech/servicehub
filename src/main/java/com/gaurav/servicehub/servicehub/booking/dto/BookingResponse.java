package com.gaurav.servicehub.servicehub.booking.dto;

import com.gaurav.servicehub.servicehub.booking.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponse(

        UUID id,

        UUID customerId,

        UUID providerId,

        UUID serviceId,

        LocalDate bookingDate,

        LocalTime startTime,

        LocalTime endTime,

        BigDecimal amount,

        BookingStatus status,

        String notes,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}