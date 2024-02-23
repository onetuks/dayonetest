package com.onetuks.dayonetest.service;

import com.onetuks.dayonetest.repository.StudentFailRepository;
import com.onetuks.dayonetest.repository.StudentPassRepository;
import com.onetuks.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StudentScoreServiceMockTest {

    @Test
    @DisplayName("첫 전째 Mock 테스트")
    void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "onetuks";
        String givenExam = "testexam";
        Integer givenKoreanScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKoreanScore,
                givenEnglishScore,
                givenMathScore
        );
    }
}
