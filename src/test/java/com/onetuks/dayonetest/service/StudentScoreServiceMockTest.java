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

    @Test
    @DisplayName("성적 저장 로직 검증")
    void saveScoreMockTest() {
        // Given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "onetuks";
        String givenExam = "testexam";
        Integer givenKoreanScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // When
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKoreanScore,
                givenEnglishScore,
                givenMathScore
        );

        // Then
        // void 메소드 테스트 -> Mockito.verify 메소드 활용
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    void saveScoreMockTest2() {
        // Given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "onetuks";
        String givenExam = "testexam";
        Integer givenKoreanScore = 10;
        Integer givenEnglishScore = 10;
        Integer givenMathScore = 10;

        // When
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKoreanScore,
                givenEnglishScore,
                givenMathScore
        );

        // Then
        // void 메소드 테스트 -> Mockito.verify 메소드 활용
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }
}
