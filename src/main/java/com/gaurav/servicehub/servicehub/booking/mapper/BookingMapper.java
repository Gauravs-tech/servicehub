package com.gaurav.servicehub.servicehub.booking.mapper;

import com.gaurav.servicehub.servicehub.booking.dto.BookingResponse;
import com.gaurav.servicehub.servicehub.booking.dto.CreateBookingRequest;
import com.gaurav.servicehub.servicehub.booking.entity.Booking;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.service.entity.Service;
import com.gaurav.servicehub.servicehub.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(
            CreateBookingRequest request,
            User customer,
            Provider provider,
            Service service
    ) {
        return Booking.builder()
                .customer(customer)
                .provider(provider)
                .service(service)
                .bookingDate(request.bookingDate())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .notes(request.notes())
                .build();
    }

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getCustomer().getId(),
                booking.getProvider().getId(),
                booking.getService().getId(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getAmount(),
                booking.getStatus(),
                booking.getNotes(),
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }
}