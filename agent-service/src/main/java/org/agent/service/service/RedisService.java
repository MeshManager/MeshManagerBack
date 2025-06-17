package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

  private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
  private final RedisTemplate<String, Object> redisTemplate;
  private static final Duration TTL = Duration.ofMinutes(3);

  public void saveWithTTL(String key) {
    try {
      logger.info("Attempting to save key '{}' to Redis with TTL {}", key, TTL);
      redisTemplate.opsForValue().set(key, "value", TTL);
      logger.info("Successfully saved key '{}' to Redis", key);
      
      // 저장 확인
      Boolean exists = redisTemplate.hasKey(key);
      logger.info("Verification - Key '{}' exists in Redis: {}", key, exists);
    } catch (Exception e) {
      logger.error("Failed to save key '{}' to Redis", key, e);
    }
  }

  public boolean exists(String key) {
    try {
      Boolean hasKey = redisTemplate.hasKey(key);
      boolean result = Boolean.TRUE.equals(hasKey);
      logger.info("Checking if key '{}' exists in Redis: {}", key, result);
      return result;
    } catch (Exception e) {
      logger.error("Failed to check if key '{}' exists in Redis", key, e);
      return false;
    }
  }

  public Set<String> getAllKeys() {
    try {
      Set<String> keys = redisTemplate.keys("*");
      logger.info("Retrieved {} keys from Redis: {}", keys != null ? keys.size() : 0, keys);
      return keys;
    } catch (Exception e) {
      logger.error("Failed to get all keys from Redis", e);
      return Set.of();
    }
  }
}
