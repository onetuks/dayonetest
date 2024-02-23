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
import org.junit.jupiter.api.Test;

class JUnitPracticeTest {

    @Test
    void assertEqualsTest() {
        String expect = "Something";
        String actual = "Something";

        assertEquals(expect, actual);
    }

    @Test
    void assertNotEqualsTest() {
        String expect = "Something";
        String actual = "Something else";

        assertNotEquals(expect, actual);
    }

    @Test
    void assertTrueTest() {
        Integer a = 10;
        Integer b = 10;

        assertTrue(a.equals(b));
    }

    @Test
    void assertFalseTest() {
        Integer a = 10;
        Integer b = 20;

        assertFalse(a.equals(b));
    }

    @Test
    void assertThrowsTest() {
        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test
    void assertNotNullTest() {
        String str = "Something";
        assertNotNull(str);
    }

    @Test
    void assertNullTest() {
        String value = null;
        assertNull(value);
    }

    @Test
    void assertIterableEqualsTest() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        assertIterableEquals(list1, list2); // Iterable 안에 값과 순서가 일치하는지 여부 검증
    }

    @Test
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
