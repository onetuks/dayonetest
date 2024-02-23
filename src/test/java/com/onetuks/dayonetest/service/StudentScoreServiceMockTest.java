package com.onetuks.dayonetest.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.onetuks.dayonetest.MyCalculator;
import com.onetuks.dayonetest.controller.response.ExamFailStudentResponse;
import com.onetuks.dayonetest.controller.response.ExamPassStudentResponse;
import com.onetuks.dayonetest.model.StudentFail;
import com.onetuks.dayonetest.model.StudentPass;
import com.onetuks.dayonetest.model.StudentScore;
import com.onetuks.dayonetest.model.StudentScoreFixture;
import com.onetuks.dayonetest.model.StudentScoreTestDataBuilder;
import com.onetuks.dayonetest.repository.StudentFailRepository;
import com.onetuks.dayonetest.repository.StudentPassRepository;
import com.onetuks.dayonetest.repository.StudentScoreRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class StudentScoreServiceMockTest {

    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    void setUp() {
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
    }

    @Test
    @DisplayName("첫 전째 Mock 테스트")
    void firstSaveScoreMockTest() {
        // given
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
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    void saveScoreMockTest() {
        // Given
        StudentScore expectStudentScore = StudentScoreTestDataBuilder
                .passed()
                .studentName("Student new Name") // Test Data Builder 패턴은 이렇게 자유도 있게 활용 가능한 것이 특징
                .build();
        StudentPass expectStudentPass = StudentPass
                .builder()
                .studentName(expectStudentScore.getStudentName())
                .exam(expectStudentScore.getExam())
                .averageScore(new MyCalculator()
                        .add(expectStudentScore.getKoreanScore().doubleValue())
                        .add(expectStudentScore.getEnglishScore().doubleValue())
                        .add(expectStudentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // When
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentPass.getExam(),
                expectStudentScore.getKoreanScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        // Then
        // void 메소드 테스트 -> Mockito.verify 메소드 활용
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        assertAll("studentScore",
                () -> assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName()),
                () -> assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam()),
                () -> assertEquals(expectStudentScore.getKoreanScore(), capturedStudentScore.getKoreanScore()),
                () -> assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore()),
                () -> assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore())
        );

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        assertAll("studentPass",
                () -> assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName()),
                () -> assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam()),
                () -> assertEquals(expectStudentPass.getAverageScore(), capturedStudentPass.getAverageScore())
        );

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    void saveScoreMockTest2() {
        // Given
        // Fixture 장점은 Builder 패턴의 높은 자유도로 인한 오류를 줄일 수 있다는 점
        StudentScore expectStudentScore = StudentScoreFixture.failed();
        StudentPass expectStudentFail = StudentPass
                .builder()
                .studentName(expectStudentScore.getStudentName())
                .exam(expectStudentScore.getExam())
                .averageScore(new MyCalculator()
                        .add(expectStudentScore.getKoreanScore().doubleValue())
                        .add(expectStudentScore.getEnglishScore().doubleValue())
                        .add(expectStudentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        // When
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKoreanScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        // Then
        // void 메소드 테스트 -> Mockito.verify 메소드 활용
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        assertAll("studentScore",
                () -> assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName()),
                () -> assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam()),
                () -> assertEquals(expectStudentScore.getKoreanScore(), capturedStudentScore.getKoreanScore()),
                () -> assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore()),
                () -> assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore())
        );

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        assertAll("studentFail",
                () -> assertEquals(expectStudentFail.getStudentName(), capturedStudentFail.getStudentName()),
                () -> assertEquals(expectStudentFail.getExam(), capturedStudentFail.getExam()),
                () -> assertEquals(expectStudentFail.getAverageScore(), capturedStudentFail.getAverageScore())
        );    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    void getPassStudentsListTest() {
        // Given
        StudentPass expectStudent1 = StudentPass.builder()
                .id(1L)
                .studentName("onetuks")
                .exam("testexam")
                .averageScore(70.0)
                .build();
        StudentPass expectStudent2 = StudentPass.builder()
                .id(2L)
                .studentName("test")
                .exam("testexam")
                .averageScore(80.0)
                .build();
        StudentPass notExpectStudent = StudentPass.builder()
                .id(3L)
                .studentName("iamnot")
                .exam("secondexam")
                .averageScore(90.0)
                .build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1, expectStudent2, notExpectStudent
        ));

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
        String givenTestExam = "testexam";

        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("onetuks").exam(givenTestExam)
                .averageScore(5.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam)
                .averageScore(59.0).build();
        StudentFail notExpectStudent = StudentFail.builder().id(3L).studentName("iamnot").exam("secondexam")
                .averageScore(90.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1, expectStudent2, notExpectStudent
        ));

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
