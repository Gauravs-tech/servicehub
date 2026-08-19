package com.gaurav.servicehub.servicehub.service.service;

import com.gaurav.servicehub.servicehub.service.dto.CreateServiceRequest;
import com.gaurav.servicehub.servicehub.service.dto.ServiceResponse;

import java.util.List;
import java.util.UUID;

public interface ServiceService {

    ServiceResponse createService(
            UUID userId,
            CreateServiceRequest request
    );

    ServiceResponse getService(UUID serviceId);

    List<ServiceResponse> getProviderServices(UUID providerId);

    List<ServiceResponse> getActiveServices();
}