package com.gaurav.servicehub.servicehub.provider.repository;

import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {

    Optional<Provider> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    List<Provider> findByStatus(ProviderStatus status);
}