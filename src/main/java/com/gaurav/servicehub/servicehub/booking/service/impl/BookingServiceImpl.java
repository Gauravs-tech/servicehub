package com.gaurav.servicehub.servicehub.booking.service.impl;

import com.gaurav.servicehub.servicehub.booking.dto.BookingResponse;
import com.gaurav.servicehub.servicehub.booking.dto.CreateBookingRequest;
import com.gaurav.servicehub.servicehub.booking.entity.Booking;
import com.gaurav.servicehub.servicehub.booking.entity.BookingStatus;
import com.gaurav.servicehub.servicehub.booking.exception.BookingValidationException;
import com.gaurav.servicehub.servicehub.booking.mapper.BookingMapper;
import com.gaurav.servicehub.servicehub.booking.repository.BookingRepository;
import com.gaurav.servicehub.servicehub.booking.service.BookingService;
import com.gaurav.servicehub.servicehub.booking.validator.BookingValidator;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.repository.ProviderRepository;
import com.gaurav.servicehub.servicehub.service.entity.Service;
import com.gaurav.servicehub.servicehub.service.repository.ServiceRepository;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final ServiceRepository serviceRepository;
    private final BookingMapper bookingMapper;
    private final BookingValidator bookingValidator;

    @Override
    public BookingResponse createBooking(
            UUID customerId,
            CreateBookingRequest request
    ) {

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new BookingValidationException(
                        "Customer not found"
                ));

        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() -> new BookingValidationException(
                        "Service not found"
                ));

        Provider provider = service.getProvider();

        bookingValidator.validateCreateBooking(
                request,
                customer,
                provider,
                service
        );

        boolean hasConflict = bookingRepository.existsOverlappingBooking(
                provider.getId(),
                request.bookingDate(),
                request.startTime(),
                request.endTime(),
                List.of(
                        BookingStatus.PENDING,
                        BookingStatus.ACCEPTED
                )
        );

        if (hasConflict) {
            throw new BookingValidationException(
                    "Provider is already booked for the selected time"
            );
        }

        Booking booking = bookingMapper.toEntity(
                request,
                customer,
                provider,
                service
        );

        booking.setAmount(service.getPrice());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getCustomerBookings(
            UUID customerId
    ) {

        return bookingRepository
                .findByCustomerId(customerId)
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getCustomerBooking(
            UUID customerId,
            UUID bookingId
    ) {

        Booking booking = bookingRepository
                .findByIdAndCustomerId(bookingId, customerId)
                .orElseThrow(() -> new BookingValidationException(
                        "Booking not found"
                ));

        return bookingMapper.toResponse(booking);
    }


    @Override
    public void cancelBooking(
            UUID customerId,
            UUID bookingId
    ) {

        Booking booking = bookingRepository
                .findByIdAndCustomerId(bookingId, customerId)
                .orElseThrow(() -> new BookingValidationException(
                        "Booking not found"
                ));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingValidationException(
                    "Only pending bookings can be cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getProviderBookings(
            UUID providerId
    ) {

        return bookingRepository
                .findByProviderId(providerId)
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingResponse acceptBooking(
            UUID providerId,
            UUID bookingId
    ) {

        Booking booking = bookingRepository
                .findByIdAndProviderId(
                        bookingId,
                        providerId
                )
                .orElseThrow(() -> new BookingValidationException(
                        "Booking not found"
                ));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingValidationException(
                    "Only pending bookings can be accepted"
            );
        }

        booking.setStatus(BookingStatus.ACCEPTED);

        return bookingMapper.toResponse(booking);
    }

    @Override
    public BookingResponse rejectBooking(
            UUID providerId,
            UUID bookingId
    ) {

        Booking booking = bookingRepository
                .findByIdAndProviderId(
                        bookingId,
                        providerId
                )
                .orElseThrow(() -> new BookingValidationException(
                        "Booking not found"
                ));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BookingValidationException(
                    "Only pending bookings can be rejected"
            );
        }

        booking.setStatus(BookingStatus.REJECTED);

        return bookingMapper.toResponse(booking);
    }

    @Override
    public BookingResponse completeBooking(
            UUID providerId,
            UUID bookingId
    ) {

        Booking booking = bookingRepository
                .findByIdAndProviderId(
                        bookingId,
                        providerId
                )
                .orElseThrow(() -> new BookingValidationException(
                        "Booking not found"
                ));

        if (booking.getStatus() != BookingStatus.ACCEPTED) {
            throw new BookingValidationException(
                    "Only accepted bookings can be completed"
            );
        }

        booking.setStatus(BookingStatus.COMPLETED);

        return bookingMapper.toResponse(booking);
    }
}