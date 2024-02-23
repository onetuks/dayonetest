package com.onetuks.dayonetest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JUnitPracticeTest {

    @Test
    void assert_Equals_Test() {
        String expect = "Something";
        String actual = "Something";

        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert Not Equals 메소드 테스트")
    void assertNotEqualsTest() {
        String expect = "Something";
        String actual = "Something else";

        assertNotEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert True 메소드 테스트")
    void assertTrueTest() {
        Integer a = 10;
        Integer b = 10;

        assertTrue(a.equals(b));
    }

    @Test
    @DisplayName("Assert False 메소드 테스트")
    void assertFalseTest() {
        Integer a = 10;
        Integer b = 20;

        assertFalse(a.equals(b));
    }

    @Test
    @DisplayName("Assert Throws 메소드 테스트")
    void assertThrowsTest() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test
    @DisplayName("Assert Not Null 메소드 테스트")
    void assertNotNullTest() {
        String str = "Something";
        assertNotNull(str);
    }

    @Test
    @DisplayName("Assert Null 메소드 테스트")
    void assertNullTest() {
        String value = null;
        assertNull(value);
    }

    @Test
    @DisplayName("Assert Iterable Equals 메소드 테스트")
    void assertIterableEqualsTest() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        assertIterableEquals(list1, list2); // Iterable 안에 값과 순서가 일치하는지 여부 검증
    }

    @Test
    @DisplayName("Assert All 메소드 테스트")
    void assertAllTest() {
        String expect = "Something";
        String actual = "Something";

        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        assertAll("Assert All", List.of(
                () -> assertEquals(expect, actual),
                () -> assertIterableEquals(list1, list2)
        ));
    }

}
