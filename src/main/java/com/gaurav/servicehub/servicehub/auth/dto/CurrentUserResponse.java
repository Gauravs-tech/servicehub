package com.gaurav.servicehub.servicehub.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CurrentUserResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;
}