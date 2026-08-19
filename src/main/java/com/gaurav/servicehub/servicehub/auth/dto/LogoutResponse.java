package com.gaurav.servicehub.servicehub.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponse {

    private String message;
}