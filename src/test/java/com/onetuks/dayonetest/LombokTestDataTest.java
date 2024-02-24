package com.onetuks.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LombokTestDataTest {

  @Test
  public void testDataTest() {
    TestData testData = new TestData();
    testData.setName("onetuks");

    Assertions.assertEquals("onetuks", testData.getName());
  }
}
