package com.gaurav.servicehub.servicehub.booking.validator;

import com.gaurav.servicehub.servicehub.booking.dto.CreateBookingRequest;
import com.gaurav.servicehub.servicehub.booking.exception.BookingValidationException;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.service.entity.Service;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.enums.UserStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class BookingValidator {

    public void validateCreateBooking(
            CreateBookingRequest request,
            User customer,
            Provider provider,
            Service service
    ) {

        validateCustomer(customer);
        validateProvider(provider);
        validateService(service);
        validateBookingDateAndTime(request);
        validateCustomerIsNotProvider(customer, provider);
    }

    private void validateCustomer(User customer) {

        if (customer == null) {
            throw new BookingValidationException(
                    "Customer not found"
            );
        }

        if (customer.getStatus() != UserStatus.ACTIVE) {
            throw new BookingValidationException(
                    "Customer account is not active"
            );
        }
    }

    private void validateProvider(Provider provider) {

        if (provider == null) {
            throw new BookingValidationException(
                    "Provider not found"
            );
        }

        if (provider.getStatus() != ProviderStatus.ACTIVE) {
            throw new BookingValidationException(
                    "Provider is not currently active"
            );
        }
    }

    private void validateService(Service service) {

        if (service == null) {
            throw new BookingValidationException(
                    "Service not found"
            );
        }

        if (!Boolean.TRUE.equals(service.getActive())) {
            throw new BookingValidationException(
                    "Service is not currently available"
            );
        }
    }

    private void validateBookingDateAndTime(
            CreateBookingRequest request
    ) {

        LocalDate bookingDate = request.bookingDate();
        LocalTime startTime = request.startTime();
        LocalTime endTime = request.endTime();

        if (bookingDate.isBefore(LocalDate.now())) {
            throw new BookingValidationException(
                    "Booking date cannot be in the past"
            );
        }

        if (!startTime.isBefore(endTime)) {
            throw new BookingValidationException(
                    "Start time must be before end time"
            );
        }

        if (bookingDate.equals(LocalDate.now())
                && !startTime.isAfter(LocalTime.now())) {

            throw new BookingValidationException(
                    "Booking start time must be in the future"
            );
        }
    }

    private void validateCustomerIsNotProvider(
            User customer,
            Provider provider
    ) {

        if (customer.getId().equals(provider.getUser().getId())) {
            throw new BookingValidationException(
                    "Provider cannot book their own service"
            );
        }
    }
}