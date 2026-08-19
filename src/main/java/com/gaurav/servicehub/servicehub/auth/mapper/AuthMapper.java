package com.gaurav.servicehub.servicehub.auth.mapper;

import com.gaurav.servicehub.servicehub.auth.dto.RegisterRequest;
import com.gaurav.servicehub.servicehub.auth.dto.RegisterResponse;
import com.gaurav.servicehub.servicehub.user.enums.Role;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.enums.UserStatus;

public final class AuthMapper {

    private AuthMapper() {
    }

    public static User toUser(RegisterRequest request, String encodedPassword) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .role(Role.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .build();
    }

    public static RegisterResponse toRegisterResponse(User user) {

        return RegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
