package com.gaurav.servicehub.servicehub.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}