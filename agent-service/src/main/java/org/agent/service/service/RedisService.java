package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private static final Duration TTL = Duration.ofMinutes(3);

  public void saveJsonWithTTL(String key, Object jsonObject) {
    redisTemplate.opsForValue().set(key, jsonObject, TTL);
  }
}
