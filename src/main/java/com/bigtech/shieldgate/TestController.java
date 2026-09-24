package com.bigtech.shieldgate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired private RateLimiterService limiter;

    @GetMapping("/api/test")
    public ResponseEntity<String> test(@RequestHeader(value="X-User-Id", defaultValue="anon") String userId) {
        long remaining = limiter.tryAcquire(userId);
        if (remaining < 0) {
            return ResponseEntity.status(429)
                    .header("X-RateLimit-Remaining", "0")
                    .body("Rate limit exceeded for " + userId);
        }
        return ResponseEntity.ok()
                .header("X-RateLimit-Remaining", String.valueOf(remaining))
                .body("OK - Remaining: " + remaining);
    }
}
