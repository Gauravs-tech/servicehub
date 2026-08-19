package com.gaurav.servicehub.servicehub.provider.mapper;

import com.gaurav.servicehub.servicehub.provider.dto.CreateProviderRequest;
import com.gaurav.servicehub.servicehub.provider.dto.ProviderResponse;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

    public Provider toEntity(
            CreateProviderRequest request,
            User user
    ) {
        return Provider.builder()
                .user(user)
                .bio(request.bio())
                .experienceYears(request.experienceYears())
                .build();
    }

    public ProviderResponse toResponse(Provider provider) {

        User user = provider.getUser();

        return new ProviderResponse(
                provider.getId(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                provider.getBio(),
                provider.getExperienceYears(),
                provider.getStatus(),
                provider.getCreatedAt(),
                provider.getUpdatedAt()
        );
    }
}