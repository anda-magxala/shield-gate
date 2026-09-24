package com.bigtech.shieldgate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class RateLimiterService {
    @Autowired private StringRedisTemplate redisTemplate;
    @Autowired private DefaultRedisScript<Long> rateLimiterScript;

    public long tryAcquire(String userId) {
        String key = "ratelimit:" + userId;
        Long remaining = redisTemplate.execute(rateLimiterScript,
                Collections.singletonList(key), "100", "60");
        return remaining == null ? -1 : remaining;
    }
}