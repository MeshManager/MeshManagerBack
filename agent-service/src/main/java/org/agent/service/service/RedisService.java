package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final Duration TTL = Duration.ofMinutes(3);

  public void saveWithTTL(String key) {
    redisTemplate.opsForValue().set(key, "value", TTL);
  }

  public boolean exists(String key) {
    Boolean hasKey = redisTemplate.hasKey(key);
    return Boolean.TRUE.equals(hasKey);
  }

  public Set<String> getAllKeys() {
    return redisTemplate.keys("*");
  }
}
