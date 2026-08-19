package com.gaurav.servicehub.servicehub.security.config;

import com.gaurav.servicehub.servicehub.common.constants.ApiPaths;
import com.gaurav.servicehub.servicehub.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ==========================================
                // CSRF
                // ==========================================
                .csrf(csrf -> csrf.disable())

                // ==========================================
                // SESSION
                // ==========================================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ==========================================
                // AUTHORIZATION
                // ==========================================
                .authorizeHttpRequests(auth -> auth

                        // ==================================
                        // PUBLIC AUTH APIs
                        // ==================================
                        .requestMatchers(
                                HttpMethod.POST,
                                ApiPaths.AUTH + ApiPaths.REGISTER,
                                ApiPaths.AUTH + ApiPaths.LOGIN,
                                ApiPaths.AUTH + ApiPaths.REFRESH
                        ).permitAll()


                        // ==================================
                        // ADMIN APIs
                        // ==================================
                        .requestMatchers(
                                ApiPaths.ADMIN + "/**"
                        ).hasRole("ADMIN")


                        // ==================================
                        // PROVIDER PROFILE APIs
                        // ==================================
                        .requestMatchers(
                                ApiPaths.PROVIDERS + "/**"
                        ).hasRole("PROVIDER")


                        // ==================================
                        // PROVIDER SERVICE CREATION
                        // ==================================
                        .requestMatchers(
                                HttpMethod.POST,
                                ApiPaths.SERVICES
                        ).hasRole("PROVIDER")


                        // ==================================
                        // CUSTOMER BOOKING CREATION
                        // ==================================
                        .requestMatchers(
                                HttpMethod.POST,
                                ApiPaths.BOOKINGS
                        ).hasRole("CUSTOMER")


                        // ==================================
                        // PROVIDER VIEW BOOKINGS
                        // ==================================
                        .requestMatchers(
                                HttpMethod.GET,
                                ApiPaths.BOOKINGS + "/provider"
                        ).hasRole("PROVIDER")


                        // ==================================
                        // PROVIDER ACCEPT BOOKING
                        // ==================================
                        .requestMatchers(
                                HttpMethod.PUT,
                                ApiPaths.BOOKINGS + "/*/accept"
                        ).hasRole("PROVIDER")

                                // ==========================================
                                // PROVIDER REJECT BOOKING
                                // ==========================================
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        ApiPaths.BOOKINGS + "/*/reject"
                                ).hasRole("PROVIDER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                ApiPaths.BOOKINGS + "/*/complete"
                        ).hasRole("PROVIDER")


                        // ==================================
                        // CUSTOMER VIEW BOOKINGS
                        // ==================================
                        .requestMatchers(
                                HttpMethod.GET,
                                ApiPaths.BOOKINGS + "/customer"
                        ).hasRole("CUSTOMER")


                        .requestMatchers(
                                HttpMethod.GET,
                                ApiPaths.BOOKINGS + "/customer/**"
                        ).hasRole("CUSTOMER")


                        // ==================================
                        // CUSTOMER CANCEL BOOKING
                        // ==================================
                        .requestMatchers(
                                HttpMethod.PUT,
                                ApiPaths.BOOKINGS + "/*/cancel"
                        ).hasRole("CUSTOMER")


                        // ==================================
                        // EVERYTHING ELSE
                        // ==================================
                        .anyRequest()
                        .authenticated()
                )

                // ==========================================
                // JWT FILTER
                // ==========================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}