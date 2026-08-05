package com.gaurav.servicehub.servicehub.user.repository;

import com.gaurav.servicehub.servicehub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String mail);

    boolean existsByEmail(String email);
}
