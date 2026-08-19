package com.gaurav.servicehub.servicehub.service.validator;

import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.service.dto.CreateServiceRequest;
import com.gaurav.servicehub.servicehub.service.exception.ServiceValidationException;
import org.springframework.stereotype.Component;

@Component
public class ServiceValidator {

    public void validateCreateService(
            CreateServiceRequest request,
            Provider provider
    ) {

        if (provider == null) {
            throw new ServiceValidationException(
                    "Provider not found"
            );
        }

        if (provider.getStatus() != ProviderStatus.ACTIVE) {
            throw new ServiceValidationException(
                    "Only active providers can create services"
            );
        }

        if (request.price() == null
                || request.price().signum() <= 0) {

            throw new ServiceValidationException(
                    "Service price must be greater than zero"
            );
        }

        if (request.estimatedDurationMinutes() == null
                || request.estimatedDurationMinutes() <= 0) {

            throw new ServiceValidationException(
                    "Service duration must be greater than zero"
            );
        }
    }
}