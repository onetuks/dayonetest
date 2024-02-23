package com.onetuks.dayonetest.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.onetuks.dayonetest.controller.response.ExamFailStudentResponse;
import com.onetuks.dayonetest.controller.response.ExamPassStudentResponse;
import com.onetuks.dayonetest.model.StudentFail;
import com.onetuks.dayonetest.model.StudentPass;
import com.onetuks.dayonetest.repository.StudentFailRepository;
import com.onetuks.dayonetest.repository.StudentPassRepository;
import com.onetuks.dayonetest.repository.StudentScoreRepository;
import java.util.List;
import java.util.stream.Stream;
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

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    void getPassStudentsListTest() {
        // Given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("onetuks").exam("testexam").averageScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("testexam").averageScore(80.0).build();
        StudentPass notExpectStudent = StudentPass.builder().id(3L).studentName("iamnot").exam("secondexam").averageScore(90.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1, expectStudent2, notExpectStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenTestExam = "testexam";

        // When
        List<ExamPassStudentResponse> result = studentScoreService.getPassStudentsList(givenTestExam);
        result.forEach(System.out::println);

        // Then
        List<ExamPassStudentResponse> expect = Stream.of(expectStudent1, expectStudent2)
                .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAverageScore()))
                .toList();

        assertIterableEquals(expect, result);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    void getFailStudentsListTest() {
        // Given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        String givenTestExam = "testexam";

        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("onetuks").exam(givenTestExam).averageScore(5.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam).averageScore(59.0).build();
        StudentFail notExpectStudent = StudentFail.builder().id(3L).studentName("iamnot").exam("secondexam").averageScore(90.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1, expectStudent2, notExpectStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        // When
        List<ExamFailStudentResponse> result = studentScoreService.getFailStudentsList(givenTestExam);
        result.forEach(System.out::println);

        // Then
        List<ExamFailStudentResponse> expect = Stream.of(expectStudent1, expectStudent2)
                .map(fail -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAverageScore()))
                .toList();

        assertIterableEquals(expect, result);
    }
}
