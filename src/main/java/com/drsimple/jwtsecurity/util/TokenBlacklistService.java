package com.drsimple.jwtsecurity.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long expiryMillis) {
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofMillis(expiryMillis));
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
