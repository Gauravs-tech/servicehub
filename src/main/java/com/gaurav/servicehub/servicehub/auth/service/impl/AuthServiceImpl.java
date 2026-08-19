package com.gaurav.servicehub.servicehub.auth.service.impl;

import com.gaurav.servicehub.servicehub.auth.dto.*;
import com.gaurav.servicehub.servicehub.auth.exception.EmailAlreadyExistsException;
import com.gaurav.servicehub.servicehub.auth.exception.InvalidCredentialsException;
import com.gaurav.servicehub.servicehub.auth.exception.UserNotFoundException;
import com.gaurav.servicehub.servicehub.auth.mapper.AuthMapper;
import com.gaurav.servicehub.servicehub.auth.service.AuthService;
import com.gaurav.servicehub.servicehub.auth.service.RefreshTokenService;
import com.gaurav.servicehub.servicehub.security.jwt.JwtService;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-expiration:604800}")
    private long refreshTokenExpiration;


    @Override
    public RegisterResponse register(RegisterRequest request) {

        // Step 1
        validateEmail(request.getEmail());

        // Step 2
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        // Step 3
        User user = AuthMapper.toUser(request, encodedPassword);

        // Step 4
        User savedUser = userRepository.save(user);

        // Step 5
        return AuthMapper.toRegisterResponse(savedUser);
    }

    private void validateEmail(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );
        String refreshToken =
                refreshTokenService.createRefreshToken(
                        user.getId().toString(),
                        Duration.ofSeconds(refreshTokenExpiration)
                );
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    public CurrentUserResponse getCurrentUser(String userId) {

        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return CurrentUserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public LoginResponse refreshToken(String refreshToken) {

        String userId =
                refreshTokenService.getUserId(refreshToken);

        if (userId == null) {
            throw new InvalidCredentialsException(
                    "Invalid or expired refresh token"
            );
        }

        User user = userRepository.findById(
                UUID.fromString(userId)
        ).orElseThrow(() ->
                new UserNotFoundException("User not found")
        );

        String accessToken = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }


    public LogoutResponse logout(LogoutRequest request) {

        boolean deleted = refreshTokenService.deleteRefreshToken(
                request.getRefreshToken()
        );


        System.out.println("Refresh token deleted: " + deleted);

        return LogoutResponse.builder()
                .message("Logout successful")
                .build();
    }

}