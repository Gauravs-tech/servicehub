package com.gaurav.servicehub.servicehub.security.filter;

import com.gaurav.servicehub.servicehub.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        // ==========================================
        // NO AUTHORIZATION HEADER
        // ==========================================

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {

            // ==========================================
            // VALIDATE TOKEN
            // ==========================================

            if (!jwtService.isTokenValid(token)) {

                filterChain.doFilter(request, response);
                return;
            }

            // ==========================================
            // EXTRACT USER INFORMATION
            // ==========================================

            String userId =
                    jwtService.extractUserId(token);

            String role =
                    jwtService.extractRole(token);

            if (userId == null || userId.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (role == null || role.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            // ==========================================
            // CREATE AUTHORITY
            // ==========================================

            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority(
                            "ROLE_" + role.toUpperCase()
                    );

            // ==========================================
            // CREATE AUTHENTICATION
            // ==========================================

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(authority)
                    );

            // ==========================================
            // SET SECURITY CONTEXT
            // ==========================================

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            // ==========================================
            // DEBUG
            // ==========================================

            System.out.println();
            System.out.println("========== JWT AUTHENTICATION ==========");
            System.out.println("Request URI       : "
                    + request.getRequestURI());
            System.out.println("User ID           : "
                    + userId);
            System.out.println("Role              : "
                    + role);
            System.out.println("Authority         : "
                    + authority.getAuthority());
            System.out.println("Authentication    : "
                    + SecurityContextHolder
                    .getContext()
                    .getAuthentication());
            System.out.println("========================================");
            System.out.println();

        } catch (Exception e) {

            System.out.println(
                    "JWT authentication failed: "
                            + e.getMessage()
            );

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}