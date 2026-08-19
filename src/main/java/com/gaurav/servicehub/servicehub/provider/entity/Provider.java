package com.gaurav.servicehub.servicehub.provider.entity;

import com.gaurav.servicehub.servicehub.common.entity.BaseEntity;
import com.gaurav.servicehub.servicehub.provider.enums.ProviderStatus;
import com.gaurav.servicehub.servicehub.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "providers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(length = 1000)
    private String bio;

    @Column(nullable = false)
    private Integer experienceYears;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderStatus status;
}