package com.gaurav.servicehub.servicehub.provider.validator;

import com.gaurav.servicehub.servicehub.provider.dto.CreateProviderRequest;
import com.gaurav.servicehub.servicehub.provider.exception.ProviderValidationException;
import com.gaurav.servicehub.servicehub.user.entity.User;
import com.gaurav.servicehub.servicehub.user.enums.Role;
import com.gaurav.servicehub.servicehub.user.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class ProviderValidator {

    public void validateCreateProvider(
            CreateProviderRequest request,
            User user
    ) {

        if (user == null) {
            throw new ProviderValidationException(
                    "User not found"
            );
        }

        if (user.getRole() != Role.PROVIDER) {
            throw new ProviderValidationException(
                    "Only users with PROVIDER role can create a provider profile"
            );
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ProviderValidationException(
                    "User account is not active"
            );
        }

        if (request.experienceYears() == null) {
            throw new ProviderValidationException(
                    "Experience years are required"
            );
        }
    }
}