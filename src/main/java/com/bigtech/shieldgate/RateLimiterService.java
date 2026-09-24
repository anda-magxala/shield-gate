package com.bigtech.shieldgate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.Collections;

@Service
public class RateLimiterService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private DefaultRedisScript<Long> script;

    @PostConstruct
    public void init() {
        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("rate_limiter.lua"));
        script.setResultType(Long.class);
    }

    public long isAllowed(String userId, int limit, int windowSeconds) {
        String key = "rate_limit:" + userId;
        return redisTemplate.execute(script, Collections.singletonList(key), String.valueOf(limit), String.valueOf(windowSeconds));
    }
}
