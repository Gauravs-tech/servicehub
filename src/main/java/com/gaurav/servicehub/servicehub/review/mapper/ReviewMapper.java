package com.gaurav.servicehub.servicehub.review.mapper;

import com.gaurav.servicehub.servicehub.booking.entity.Booking;
import com.gaurav.servicehub.servicehub.review.dto.CreateReviewRequest;
import com.gaurav.servicehub.servicehub.review.dto.ReviewResponse;
import com.gaurav.servicehub.servicehub.review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // ==========================================
    // CREATE REVIEW ENTITY
    // ==========================================

    public Review toEntity(
            CreateReviewRequest request,
            Booking booking
    ) {

        return Review.builder()
                .booking(booking)
                .customer(booking.getCustomer())
                .provider(booking.getProvider())
                .service(booking.getService())
                .rating(request.rating())
                .comment(request.comment())
                .build();
    }

    // ==========================================
    // REVIEW ENTITY -> RESPONSE
    // ==========================================

    public ReviewResponse toResponse(
            Review review
    ) {

        return new ReviewResponse(
                review.getId(),
                review.getBooking().getId(),
                review.getCustomer().getId(),
                review.getProvider().getId(),
                review.getService().getId(),
                review.getRating(),
                review.getComment()
        );
    }
}