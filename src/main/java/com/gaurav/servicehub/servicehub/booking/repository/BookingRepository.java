package com.gaurav.servicehub.servicehub.booking.repository;

import com.gaurav.servicehub.servicehub.booking.entity.Booking;
import com.gaurav.servicehub.servicehub.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByCustomerId(UUID customerId);

    List<Booking> findByProviderId(UUID providerId);

    List<Booking> findByProviderIdAndStatus(
            UUID providerId,
            BookingStatus status
    );

    Optional<Booking> findByIdAndCustomerId(
            UUID bookingId,
            UUID customerId
    );

    Optional<Booking> findByIdAndProviderId(
            UUID bookingId,
            UUID providerId
    );

    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.provider.id = :providerId
              AND b.bookingDate = :bookingDate
              AND b.status IN :statuses
              AND b.startTime < :endTime
              AND b.endTime > :startTime
            """)
    boolean existsOverlappingBooking(
            @Param("providerId") UUID providerId,
            @Param("bookingDate") LocalDate bookingDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statuses") List<BookingStatus> statuses
    );
}