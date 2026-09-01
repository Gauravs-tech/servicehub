package com.gaurav.servicehub.servicehub.review.service;

import com.gaurav.servicehub.servicehub.review.dto.CreateReviewRequest;
import com.gaurav.servicehub.servicehub.review.dto.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    ReviewResponse createReview(
            UUID customerId,
            CreateReviewRequest request
    );

    List<ReviewResponse> getProviderReviews(
            UUID providerId
    );

    List<ReviewResponse> getCustomerReviews(
            UUID customerId
    );
}