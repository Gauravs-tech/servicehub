package com.gaurav.servicehub.servicehub.admin.service.impl;

import com.gaurav.servicehub.servicehub.admin.exception.AdminValidationException;
import com.gaurav.servicehub.servicehub.admin.service.AdminService;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final ProviderRepository providerRepository;

    @Override
    public void approveProvider(UUID providerId) {

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() ->
                        new AdminValidationException(
                                "Provider not found"
                        )
                );

        if (provider.getStatus() != ProviderStatus.PENDING) {
            throw new AdminValidationException(
                    "Only pending providers can be approved"
            );
        }

        provider.setStatus(ProviderStatus.ACTIVE);

        providerRepository.save(provider);
    }
}