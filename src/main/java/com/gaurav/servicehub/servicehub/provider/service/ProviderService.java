package com.gaurav.servicehub.servicehub.provider.service;

import com.gaurav.servicehub.servicehub.provider.dto.CreateProviderRequest;
import com.gaurav.servicehub.servicehub.provider.dto.ProviderResponse;

import java.util.UUID;

public interface ProviderService {

    ProviderResponse createProvider(
            UUID userId,
            CreateProviderRequest request
    );

    ProviderResponse getProvider(UUID providerId);

    ProviderResponse getMyProvider(UUID userId);
}