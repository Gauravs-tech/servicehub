package com.gaurav.servicehub.servicehub.service.repository;

import com.gaurav.servicehub.servicehub.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceRepository
        extends JpaRepository<Service, UUID> {

    List<Service> findByProviderId(UUID providerId);

    List<Service> findByActiveTrue();
}