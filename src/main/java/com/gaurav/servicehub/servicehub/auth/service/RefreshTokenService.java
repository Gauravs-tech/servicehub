package com.gaurav.servicehub.servicehub.auth.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final String KEY_PREFIX = "refresh_token:";

    private final StringRedisTemplate redisTemplate;

    public RefreshTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createRefreshToken(
            String userId,
            Duration expiration
    ) {

        String refreshToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                KEY_PREFIX + refreshToken,
                userId,
                expiration
        );

        return refreshToken;
    }

    public String getUserId(String refreshToken) {

        return redisTemplate.opsForValue()
                .get(KEY_PREFIX + refreshToken);
    }

    public boolean deleteRefreshToken(String refreshToken) {

        return Boolean.TRUE.equals(
                redisTemplate.delete(
                        KEY_PREFIX + refreshToken
                )
        );
    }
}