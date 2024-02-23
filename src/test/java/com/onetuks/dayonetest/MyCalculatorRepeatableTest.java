package com.onetuks.dayonetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MyCalculatorRepeatableTest {

    @DisplayName("덧셈을 5번 반복하며 테스트")
    @RepeatedTest(5)
    void repeatedAddTest() {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(10.0);

        // Assert - 단언
        assertEquals(10.0, myCalculator.getResult());
    }

    @DisplayName("덧셈을 5번 파라미터를 넣어주며 반복하며 테스트")
    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    void parameterizedTest(Double addValue, Double expectValue) {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(addValue);

        // Assert - 단언
        assertEquals(expectValue, myCalculator.getResult());
    }

    private static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
            Arguments.of(10.0, 10.0),
            Arguments.of(8.0, 8.0),
            Arguments.of(4.0, 4.0),
            Arguments.of(16.0, 16.0),
            Arguments.of(13.0, 13.0)
        );
    }

    @DisplayName("파라미터를 넣으며 사칙연산을 2번 테스트")
    @ParameterizedTest
    @MethodSource("parmeterizedCompliactedCalculateTestParameters")
    void parameterizedCompliactedCalculateTest(
            Double addValue,
            Double minusValue,
            Double multiplyValue,
            Double divideValue,
            Double expectValue
    ) {
        // Given
        MyCalculator myCalculator = new MyCalculator(0.0);

        // When
        Double result = myCalculator
                .add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        // Then
        assertEquals(expectValue, result);
    }

    private static Stream<Arguments> parmeterizedCompliactedCalculateTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(20.0, 5.0, 2.0, 2.0, 15.0)
        );
    }

}
