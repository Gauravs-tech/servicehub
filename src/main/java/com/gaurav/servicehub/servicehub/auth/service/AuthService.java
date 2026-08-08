package com.gaurav.servicehub.servicehub.auth.service;

import com.gaurav.servicehub.servicehub.auth.dto.*;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest login);

    CurrentUserResponse getCurrentUser(String userId);

    LoginResponse refreshToken(String refreshToken);

    LogoutResponse logout(LogoutRequest request);

}