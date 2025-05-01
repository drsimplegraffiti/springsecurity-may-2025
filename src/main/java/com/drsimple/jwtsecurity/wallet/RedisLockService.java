package com.drsimple.jwtsecurity.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisLockService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean acquireLock(String key, String value, long expirationMillis) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofMillis(expirationMillis));
    }

    public void releaseLock(String key, String value) {
        // Ensure only the holder of the lock can release it
        String currentValue = redisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
        }
    }
}
