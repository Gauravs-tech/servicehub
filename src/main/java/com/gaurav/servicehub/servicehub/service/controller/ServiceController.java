package com.gaurav.servicehub.servicehub.service.controller;

import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;
import com.gaurav.servicehub.servicehub.common.util.ApiResponseUtil;
import com.gaurav.servicehub.servicehub.service.dto.CreateServiceRequest;
import com.gaurav.servicehub.servicehub.service.dto.ServiceResponse;
import com.gaurav.servicehub.servicehub.service.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.SERVICES)
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(
            @Valid @RequestBody CreateServiceRequest request,
            Authentication authentication
    ) {

        UUID userId = UUID.fromString(authentication.getName());

        ServiceResponse response =
                serviceService.createService(
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseUtil.success(
                                response,
                                "Service created successfully"
                        )
                );
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getService(
            @PathVariable UUID serviceId
    ) {

        ServiceResponse response =
                serviceService.getService(serviceId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Service retrieved successfully"
                )
        );
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>>
    getProviderServices(
            @PathVariable UUID providerId
    ) {

        List<ServiceResponse> response =
                serviceService.getProviderServices(providerId);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Provider services retrieved successfully"
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponse>>>
    getActiveServices() {

        List<ServiceResponse> response =
                serviceService.getActiveServices();

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Active services retrieved successfully"
                )
        );
    }
}