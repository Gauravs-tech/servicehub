package com.gaurav.servicehub.servicehub.booking.controller;

import com.gaurav.servicehub.servicehub.booking.dto.BookingResponse;
import com.gaurav.servicehub.servicehub.booking.dto.CreateBookingRequest;
import com.gaurav.servicehub.servicehub.booking.service.BookingService;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final ProviderRepository providerRepository;


    // =========================================================
    // CUSTOMER
    // =========================================================

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody CreateBookingRequest request,
            Authentication authentication
    ) {

        UUID customerId = UUID.fromString(
                authentication.getName()
        );

        BookingResponse response =
                bookingService.createBooking(
                        customerId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/customer")
    public ResponseEntity<List<BookingResponse>> getCustomerBookings(
            Authentication authentication
    ) {

        UUID customerId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                bookingService.getCustomerBookings(customerId)
        );
    }


    @GetMapping("/customer/{bookingId}")
    public ResponseEntity<BookingResponse> getCustomerBooking(
            @PathVariable UUID bookingId,
            Authentication authentication
    ) {

        UUID customerId = UUID.fromString(
                authentication.getName()
        );

        return ResponseEntity.ok(
                bookingService.getCustomerBooking(
                        customerId,
                        bookingId
                )
        );
    }


    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable UUID bookingId,
            Authentication authentication
    ) {

        UUID customerId = UUID.fromString(
                authentication.getName()
        );

        bookingService.cancelBooking(
                customerId,
                bookingId
        );

        return ResponseEntity.noContent().build();
    }


    // =========================================================
    // PROVIDER
    // =========================================================

    @GetMapping("/provider")
    public ResponseEntity<List<BookingResponse>> getProviderBookings(
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        Provider provider = providerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Provider profile not found"
                        )
                );

        return ResponseEntity.ok(
                bookingService.getProviderBookings(
                        provider.getId()
                )
        );
    }


    @PutMapping("/{bookingId}/accept")
    public ResponseEntity<BookingResponse> acceptBooking(
            @PathVariable UUID bookingId,
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        Provider provider = providerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Provider profile not found"
                        )
                );

        BookingResponse response =
                bookingService.acceptBooking(
                        provider.getId(),
                        bookingId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> rejectBooking(
            @PathVariable UUID bookingId,
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        Provider provider = providerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Provider profile not found"
                        )
                );

        BookingResponse response =
                bookingService.rejectBooking(
                        provider.getId(),
                        bookingId
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponse> completeBooking(
            @PathVariable UUID bookingId,
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(
                authentication.getName()
        );

        Provider provider = providerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Provider profile not found"
                        )
                );

        BookingResponse response =
                bookingService.completeBooking(
                        provider.getId(),
                        bookingId
                );

        return ResponseEntity.ok(response);
    }
}