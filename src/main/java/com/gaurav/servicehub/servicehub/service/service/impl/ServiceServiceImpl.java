package com.gaurav.servicehub.servicehub.service.service.impl;

import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.provider.repository.ProviderRepository;
import com.gaurav.servicehub.servicehub.service.dto.CreateServiceRequest;
import com.gaurav.servicehub.servicehub.service.dto.ServiceResponse;
import com.gaurav.servicehub.servicehub.service.entity.Service;
import com.gaurav.servicehub.servicehub.service.exception.ServiceValidationException;
import com.gaurav.servicehub.servicehub.service.mapper.ServiceMapper;
import com.gaurav.servicehub.servicehub.service.repository.ServiceRepository;
import com.gaurav.servicehub.servicehub.service.service.ServiceService;
import com.gaurav.servicehub.servicehub.service.validator.ServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ProviderRepository providerRepository;
    private final ServiceMapper serviceMapper;
    private final ServiceValidator serviceValidator;

    @Override
    public ServiceResponse createService(
            UUID userId,
            CreateServiceRequest request
    ) {

        Provider provider = providerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ServiceValidationException(
                                "Provider profile not found"
                        )
                );

        serviceValidator.validateCreateService(
                request,
                provider
        );

        Service service = serviceMapper.toEntity(
                request,
                provider
        );

        Service savedService =
                serviceRepository.save(service);

        return serviceMapper.toResponse(savedService);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse getService(UUID serviceId) {

        Service service = serviceRepository
                .findById(serviceId)
                .orElseThrow(() ->
                        new ServiceValidationException(
                                "Service not found"
                        )
                );

        return serviceMapper.toResponse(service);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponse> getProviderServices(
            UUID providerId
    ) {

        return serviceRepository
                .findByProviderId(providerId)
                .stream()
                .map(serviceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceResponse> getActiveServices() {

        return serviceRepository
                .findByActiveTrue()
                .stream()
                .map(serviceMapper::toResponse)
                .toList();
    }
}