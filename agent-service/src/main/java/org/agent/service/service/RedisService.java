package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;

import java.time.Duration;
import java.util.Set;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RedisService {

  private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
  private final RedisTemplate<String, Object> redisTemplate;
  private static final Duration TTL = Duration.ofMinutes(3);

  public boolean testConnection() {
    try {
      String pong = redisTemplate.getConnectionFactory().getConnection().ping();
      logger.info("Redis connection test successful: {}", pong);
      return true;
    } catch (Exception e) {
      logger.error("Redis connection test failed", e);
      return false;
    }
  }

  public void saveWithTTL(String key) {
    try {
      logger.info("Attempting to save key '{}' to Redis with TTL {}", key, TTL);
      
      // 연결 확인
      if (!testConnection()) {
        logger.error("Redis connection is not available for key '{}'", key);
        throw new RuntimeException("Redis connection failed");
      }
      
      // 에이전트 정보를 JSON으로 저장
      String agentInfo = String.format("{"
          + "\"agentName\":\"%s\","
          + "\"status\":\"CONNECTED\","
          + "\"lastHeartbeat\":\"%s\""
          + "}", key, java.time.LocalDateTime.now().toString());
      
      redisTemplate.opsForValue().set(key, agentInfo, TTL);
      logger.info("Successfully saved key '{}' to Redis with value: {}", key, agentInfo);
      
      // 저장 확인
      Boolean exists = redisTemplate.hasKey(key);
      logger.info("Verification - Key '{}' exists in Redis: {}", key, exists);
      
      if (!Boolean.TRUE.equals(exists)) {
        throw new RuntimeException("Failed to verify key existence after save");
      }
      
    } catch (RedisConnectionFailureException e) {
      logger.error("Redis connection failed for key '{}': {}", key, e.getMessage());
      throw new RuntimeException("Redis connection failed: " + e.getMessage());
    } catch (Exception e) {
      logger.error("Failed to save key '{}' to Redis", key, e);
      throw new RuntimeException("Failed to save to Redis: " + e.getMessage());
    }
  }

  public boolean exists(String key) {
    try {
      Boolean hasKey = redisTemplate.hasKey(key);
      boolean result = Boolean.TRUE.equals(hasKey);
      logger.info("Checking if key '{}' exists in Redis: {}", key, result);
      return result;
    } catch (RedisConnectionFailureException e) {
      logger.error("Redis connection failed while checking key '{}': {}", key, e.getMessage());
      return false;
    } catch (Exception e) {
      logger.error("Failed to check if key '{}' exists in Redis", key, e);
      return false;
    }
  }

  public Set<String> getAllKeys() {
    try {
      Set<String> keys = redisTemplate.keys("*");
      logger.info("Retrieved {} keys from Redis: {}", keys != null ? keys.size() : 0, keys);
      return keys != null ? keys : Collections.emptySet();
    } catch (RedisConnectionFailureException e) {
      logger.error("Redis connection failed while getting all keys: {}", e.getMessage());
      return Collections.emptySet();
    } catch (Exception e) {
      logger.error("Failed to get all keys from Redis", e);
      return Collections.emptySet();
    }
  }

  public Object getValue(String key) {
    try {
      Object value = redisTemplate.opsForValue().get(key);
      logger.info("Retrieved value for key '{}': {}", key, value);
      return value;
    } catch (Exception e) {
      logger.error("Failed to get value for key '{}' from Redis", key, e);
      return null;
    }
  }
}
