package com.gaurav.servicehub.servicehub.service.entity;

import com.gaurav.servicehub.servicehub.common.entity.BaseEntity;
import com.gaurav.servicehub.servicehub.provider.entity.Provider;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer estimatedDurationMinutes;

    @Column(nullable = false)
    private Boolean active;
}