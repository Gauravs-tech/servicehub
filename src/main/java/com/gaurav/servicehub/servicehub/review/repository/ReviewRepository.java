package com.gaurav.servicehub.servicehub.review.repository;

import com.gaurav.servicehub.servicehub.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Optional<Review> findByBookingId(UUID bookingId);

    boolean existsByBookingId(UUID bookingId);

    List<Review> findByProviderId(UUID providerId);

    List<Review> findByServiceId(UUID serviceId);

    List<Review> findByCustomerId(UUID customerId);
}