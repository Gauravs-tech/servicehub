package com.gaurav.servicehub.servicehub.provider.controller;

import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;
import com.gaurav.servicehub.servicehub.common.util.ApiResponseUtil;
import com.gaurav.servicehub.servicehub.provider.dto.CreateProviderRequest;
import com.gaurav.servicehub.servicehub.provider.dto.ProviderResponse;
import com.gaurav.servicehub.servicehub.provider.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.PROVIDERS)
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProviderResponse>> createProvider(
            @Valid @RequestBody CreateProviderRequest request,
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(authentication.getName());

        ProviderResponse response =
                providerService.createProvider(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseUtil.success(
                                response,
                                "Provider profile created successfully"
                        )
                );
    }

    @GetMapping("/{providerId}")
    public ResponseEntity<ApiResponse<ProviderResponse>> getProvider(
            @PathVariable UUID providerId
    ) {

        ProviderResponse response =
                providerService.getProvider(providerId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Provider retrieved successfully"
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProviderResponse>> getMyProvider(
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(authentication.getName());

        ProviderResponse response =
                providerService.getMyProvider(userId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Provider profile retrieved successfully"
                )
        );
    }

    @GetMapping("/debug-auth")
    public String debugAuthentication(
            Authentication authentication
    ) {

        System.out.println("USER: " + authentication.getName());

        System.out.println(
                "AUTHORITIES: "
                        + authentication.getAuthorities()
        );

        return "User: "
                + authentication.getName()
                + " | Authorities: "
                + authentication.getAuthorities();
    }
}