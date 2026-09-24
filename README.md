# Shield-Gate - Distributed API Rate Limiter

High-performance distributed rate limiter using Redis + Lua + Spring Boot.

## Architecture
Client -> Spring Boot API -> Lua Script (Atomic INCR + EXPIRE) -> Redis

## Why This Project?
Prevents API abuse across multiple servers. Uses Lua for atomic operations - no race conditions.

## Tech Stack
Java 17, Spring Boot 3.2, Redis 7, Lua, Maven

## How To Run
```bash
# Terminal 1
/tmp/redis-stable/src/redis-server

# Terminal 2
mvn spring-boot:run

# Test - should show OK - Remaining: 99
curl -H "X-User-Id: user1" http://localhost:8080/api/test

# Load test - after 100 requests blocks
for i in {1..102}; do curl -s -H "X-User-Id: demo" http://localhost:8080/api/test; echo ""; done