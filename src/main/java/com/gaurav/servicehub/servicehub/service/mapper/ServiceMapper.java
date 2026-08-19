package com.gaurav.servicehub.servicehub.service.mapper;

import com.gaurav.servicehub.servicehub.service.dto.CreateServiceRequest;
import com.gaurav.servicehub.servicehub.service.dto.ServiceResponse;
import com.gaurav.servicehub.servicehub.service.entity.Service;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Service toEntity(
            CreateServiceRequest request,
            Provider provider
    ) {

        return Service.builder()
                .provider(provider)
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .estimatedDurationMinutes(
                        request.estimatedDurationMinutes()
                )
                .active(true)
                .build();
    }

    public ServiceResponse toResponse(Service service) {

        Provider provider = service.getProvider();
        User user = provider.getUser();

        String providerName =
                user.getFirstName() + " " + user.getLastName();

        return new ServiceResponse(
                service.getId(),
                provider.getId(),
                providerName,
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getEstimatedDurationMinutes(),
                service.getActive(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}