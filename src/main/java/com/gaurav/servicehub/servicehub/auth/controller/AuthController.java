package com.gaurav.servicehub.servicehub.auth.controller;

import com.gaurav.servicehub.servicehub.auth.dto.*;
import com.gaurav.servicehub.servicehub.auth.service.AuthService;
import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.common.dto.ApiResponse;
import com.gaurav.servicehub.servicehub.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PostMapping(ApiPaths.LOGIN)
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity
                .ok(ApiResponseUtil.success(
                        response,
                        "Login successful"
                ));
    }

    @GetMapping(ApiPaths.ME)
    public ResponseEntity<ApiResponse<CurrentUserResponse>> getCurrentUser(
            Authentication authentication
    ) {

        CurrentUserResponse response =
                authService.getCurrentUser(
                        authentication.getName()
                );

        return ResponseEntity
                .ok(
                        ApiResponseUtil.success(
                                response,
                                "Current user retrieved successfully"
                        )
                );
    }

    @PostMapping(ApiPaths.REFRESH)
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {

        LoginResponse response =
                authService.refreshToken(request.getRefreshToken());

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Token refreshed successfully"
                )
        );
    }

    @PostMapping(ApiPaths.LOGOUT)
    public ResponseEntity<ApiResponse<LogoutResponse>> logout(
            @Valid @RequestBody LogoutRequest request
    ) {

        LogoutResponse response = authService.logout(request);

        return ResponseEntity.ok(
                ApiResponseUtil.success(
                        response,
                        "Logout successful"
                )
        );
    }

}