package com.gaurav.servicehub.servicehub.review.controller;

import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.review.dto.CreateReviewRequest;
import com.gaurav.servicehub.servicehub.review.dto.ReviewResponse;
import com.gaurav.servicehub.servicehub.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // ==========================================
    // CREATE REVIEW
    // ==========================================

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            Authentication authentication,
            @Valid @RequestBody CreateReviewRequest request
    ) {

        UUID customerId =
                UUID.fromString(authentication.getName());

        ReviewResponse response =
                reviewService.createReview(
                        customerId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================================
    // GET REVIEWS FOR A PROVIDER
    // ==========================================

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ReviewResponse>> getProviderReviews(
            @PathVariable UUID providerId
    ) {

        return ResponseEntity.ok(
                reviewService.getProviderReviews(providerId)
        );
    }

    // ==========================================
    // GET MY REVIEWS
    // ==========================================

    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(
            Authentication authentication
    ) {

        UUID customerId =
                UUID.fromString(authentication.getName());

        return ResponseEntity.ok(
                reviewService.getCustomerReviews(customerId)
        );
    }
}