package com.gaurav.servicehub.servicehub.auth.controller;

import com.gaurav.servicehub.servicehub.auth.dto.RegisterRequest;
import com.gaurav.servicehub.servicehub.auth.dto.RegisterResponse;
import com.gaurav.servicehub.servicehub.auth.service.AuthService;
import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;
import com.gaurav.servicehub.servicehub.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiPaths.REGISTER)
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponseUtil.success(
                                response,
                                "Registration successful"
                        )
                );
    }
}