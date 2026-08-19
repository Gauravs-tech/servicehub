package com.gaurav.servicehub.servicehub.provider.service.impl;

import com.gaurav.servicehub.servicehub.provider.dto.CreateProviderRequest;
import com.gaurav.servicehub.servicehub.provider.dto.ProviderResponse;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.provider.exception.ProviderValidationException;
import com.gaurav.servicehub.servicehub.provider.mapper.ProviderMapper;
import com.gaurav.servicehub.servicehub.provider.repository.ProviderRepository;
import com.gaurav.servicehub.servicehub.provider.service.ProviderService;
import com.gaurav.servicehub.servicehub.provider.validator.ProviderValidator;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final UserRepository userRepository;
    private final ProviderMapper providerMapper;
    private final ProviderValidator providerValidator;

    @Override
    public ProviderResponse createProvider(
            UUID userId,
            CreateProviderRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProviderValidationException(
                        "User not found"
                ));

        if (providerRepository.existsByUserId(userId)) {
            throw new ProviderValidationException(
                    "Provider profile already exists"
            );
        }

        providerValidator.validateCreateProvider(
                request,
                user
        );

        Provider provider = providerMapper.toEntity(
                request,
                user
        );

        provider.setStatus(ProviderStatus.PENDING);

        Provider savedProvider = providerRepository.save(provider);

        return providerMapper.toResponse(savedProvider);
    }

    @Override
    @Transactional(readOnly = true)
    public ProviderResponse getProvider(UUID providerId) {

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderValidationException(
                        "Provider not found"
                ));

        return providerMapper.toResponse(provider);
    }

    @Override
    @Transactional(readOnly = true)
    public ProviderResponse getMyProvider(UUID userId) {

        Provider provider = providerRepository.findByUserId(userId)
                .orElseThrow(() -> new ProviderValidationException(
                        "Provider profile not found"
                ));

        return providerMapper.toResponse(provider);
    }
}