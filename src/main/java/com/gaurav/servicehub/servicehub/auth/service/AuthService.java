package com.gaurav.servicehub.servicehub.auth.service;

import com.gaurav.servicehub.servicehub.auth.dto.RegisterRequest;
import com.gaurav.servicehub.servicehub.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

}