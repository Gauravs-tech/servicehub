package com.gaurav.servicehub.servicehub.booking.service;

import com.gaurav.servicehub.servicehub.booking.dto.BookingResponse;
import com.gaurav.servicehub.servicehub.booking.dto.CreateBookingRequest;

import java.util.List;
import java.util.UUID;

public interface BookingService {

    // Customer operations

    BookingResponse createBooking(
            UUID customerId,
            CreateBookingRequest request
    );

    List<BookingResponse> getCustomerBookings(
            UUID customerId
    );

    BookingResponse getCustomerBooking(
            UUID customerId,
            UUID bookingId
    );

    void cancelBooking(
            UUID customerId,
            UUID bookingId
    );


    // Provider operations

    List<BookingResponse> getProviderBookings(
            UUID providerId
    );

    BookingResponse acceptBooking(
            UUID providerId,
            UUID bookingId
    );

    BookingResponse rejectBooking(
            UUID providerId,
            UUID bookingId
    );

    BookingResponse completeBooking(
            UUID providerId,
            UUID bookingId
    );
}