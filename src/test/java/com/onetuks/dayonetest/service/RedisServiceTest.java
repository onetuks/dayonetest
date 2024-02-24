package com.onetuks.dayonetest.service;

import com.onetuks.dayonetest.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RedisServiceTest extends IntegrationTest {

  @Autowired private RedisService redisService;

  @Test
  @DisplayName("Redis Get / Set 테스트")
  void redisGetSetTest() {
    // Given
    String expectValue = "hello";
    String key = "hi";

    // When
    redisService.set(key, expectValue);

    // Then
    String actualValue = redisService.get(key);

    Assertions.assertEquals(expectValue, actualValue);
  }
}
