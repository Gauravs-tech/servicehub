package com.gaurav.servicehub.servicehub.review.service.impl;

import com.gaurav.servicehub.servicehub.booking.entity.Booking;
import com.gaurav.servicehub.servicehub.booking.entity.BookingStatus;
import com.gaurav.servicehub.servicehub.booking.repository.BookingRepository;
import com.gaurav.servicehub.servicehub.review.dto.CreateReviewRequest;
import com.gaurav.servicehub.servicehub.review.dto.ReviewResponse;
import com.gaurav.servicehub.servicehub.review.entity.Review;
import com.gaurav.servicehub.servicehub.review.exception.ReviewValidationException;
import com.gaurav.servicehub.servicehub.review.mapper.ReviewMapper;
import com.gaurav.servicehub.servicehub.review.repository.ReviewRepository;
import com.gaurav.servicehub.servicehub.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;

    // ==========================================
    // CREATE REVIEW
    // ==========================================

    @Override
    public ReviewResponse createReview(
            UUID customerId,
            CreateReviewRequest request
    ) {

        // ------------------------------------------
        // 1. Find booking
        // ------------------------------------------

        Booking booking = bookingRepository
                .findByIdAndCustomerId(
                        request.bookingId(),
                        customerId
                )
                .orElseThrow(() ->
                        new ReviewValidationException(
                                "Booking not found"
                        )
                );

        // ------------------------------------------
        // 2. Booking must be completed
        // ------------------------------------------

        if (booking.getStatus() != BookingStatus.COMPLETED) {

            throw new ReviewValidationException(
                    "Only completed bookings can be reviewed"
            );
        }

        // ------------------------------------------
        // 3. Check duplicate review
        // ------------------------------------------

        if (reviewRepository
                .findByBookingId(request.bookingId())
                .isPresent()) {

            throw new ReviewValidationException(
                    "Review already exists for this booking"
            );
        }

        // ------------------------------------------
        // 4. Create review
        // ------------------------------------------

        Review review =
                reviewMapper.toEntity(
                        request,
                        booking
                );

        Review savedReview =
                reviewRepository.save(review);

        // ------------------------------------------
        // 5. Return response
        // ------------------------------------------

        return reviewMapper.toResponse(savedReview);
    }

    // ==========================================
    // GET PROVIDER REVIEWS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getProviderReviews(
            UUID providerId
    ) {

        return reviewRepository
                .findByProviderId(providerId)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    // ==========================================
    // GET CUSTOMER REVIEWS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getCustomerReviews(
            UUID customerId
    ) {

        return reviewRepository
                .findByCustomerId(customerId)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}