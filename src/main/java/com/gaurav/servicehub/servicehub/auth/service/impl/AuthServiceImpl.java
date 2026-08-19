package com.gaurav.servicehub.servicehub.auth.service.impl;

import com.gaurav.servicehub.servicehub.auth.dto.RegisterRequest;
import com.gaurav.servicehub.servicehub.auth.dto.RegisterResponse;
import com.gaurav.servicehub.servicehub.auth.exception.EmailAlreadyExistsException;
import com.gaurav.servicehub.servicehub.auth.mapper.AuthMapper;
import com.gaurav.servicehub.servicehub.auth.service.AuthService;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

}