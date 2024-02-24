package com.onetuks.dayonetest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyCalculatorTest {

  @Test
  void addTest() {
    // AAA 패턴

    // Arrange - 준비
    MyCalculator myCalculator = new MyCalculator();

    // Act - 행동 / 연산
    myCalculator.add(10.0);

    // Assert = 단언 / 검증
    assertEquals(10.0, myCalculator.getResult());
  }

  @Test
  void minusTest() {
    // given
    MyCalculator myCalculator = new MyCalculator(10.0);

    // when
    myCalculator.minus(5.0);

    // then
    assertEquals(5.0, myCalculator.getResult());
  }

  @Test
  void multiplyTest() {
    MyCalculator myCalculator = new MyCalculator(2.0);

    myCalculator.multiply(2.0);

    assertEquals(4.0, myCalculator.getResult());
  }

  @Test
  void divideTest() {
    MyCalculator myCalculator = new MyCalculator(10.0);

    myCalculator.divide(2.0);

    assertEquals(5.0, myCalculator.getResult());
  }

  @Test
  void complicatedCalculateTest() {
    // given
    MyCalculator myCalculator = new MyCalculator(0.0);

    // when
    Double result = myCalculator.add(10.0).minus(4.0).multiply(2.0).divide(3.0).getResult();

    // then
    assertEquals(4.0, result);
  }

  @Test
  @DisplayName("MyCalculator 0으로 나누었을 때에는 ZeroDivisionException이 발생해야 한다")
  void divideZeroTest() {
    // Given
    MyCalculator myCalculator = new MyCalculator(10.0);

    // When & Then
    assertThrows(MyCalculator.ZeroDevisionException.class, () -> myCalculator.divide(0.0));
  }
}
