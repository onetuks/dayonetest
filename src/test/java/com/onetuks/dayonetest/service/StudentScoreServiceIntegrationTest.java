package com.onetuks.dayonetest.service;

import com.onetuks.dayonetest.IntegrationTest;
import com.onetuks.dayonetest.MyCalculator;
import com.onetuks.dayonetest.controller.response.ExamFailStudentResponse;
import com.onetuks.dayonetest.controller.response.ExamPassStudentResponse;
import com.onetuks.dayonetest.model.StudentScore;
import com.onetuks.dayonetest.model.StudentScoreFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class StudentScoreServiceIntegrationTest extends IntegrationTest {

  @Autowired private StudentScoreService studentScoreService;

  @Autowired private EntityManager entityManager;

  @Test
  void savePassedStudentScoreTest() {
    // Given
    StudentScore studentScore = StudentScoreFixture.passed();

    // When
    studentScoreService.saveScore(
        studentScore.getStudentName(),
        studentScore.getExam(),
        studentScore.getKoreanScore(),
        studentScore.getEnglishScore(),
        studentScore.getMathScore());
    entityManager.flush();
    entityManager.clear();

    // Then
    List<ExamPassStudentResponse> passStudentsList =
        studentScoreService.getPassStudentsList(studentScore.getExam());

    Assertions.assertEquals(1, passStudentsList.size());

    ExamPassStudentResponse examPassStudentResponse = passStudentsList.get(0);

    MyCalculator calculator = new MyCalculator();

    Assertions.assertEquals(
        studentScore.getStudentName(), examPassStudentResponse.getStudentName());
    Assertions.assertEquals(
        calculator
            .add(studentScore.getKoreanScore().doubleValue())
            .add(studentScore.getEnglishScore().doubleValue())
            .add(studentScore.getMathScore().doubleValue())
            .divide(3.0)
            .getResult(),
        examPassStudentResponse.getAverageScore());
  }

  @Test
  void saveFailedStudentScoreTest() {
    // Given
    StudentScore studentScore = StudentScoreFixture.failed();

    // When
    studentScoreService.saveScore(
        studentScore.getStudentName(),
        studentScore.getExam(),
        studentScore.getKoreanScore(),
        studentScore.getEnglishScore(),
        studentScore.getMathScore());
    entityManager.flush();
    entityManager.clear();

    // Then
    List<ExamFailStudentResponse> failStudentsList =
        studentScoreService.getFailStudentsList(studentScore.getExam());

    Assertions.assertEquals(1, failStudentsList.size());

    ExamFailStudentResponse examFailStudentResponse = failStudentsList.get(0);

    MyCalculator calculator = new MyCalculator();

    Assertions.assertEquals(
        studentScore.getStudentName(), examFailStudentResponse.getStudentName());
    Assertions.assertEquals(
        calculator
            .add(studentScore.getKoreanScore().doubleValue())
            .add(studentScore.getEnglishScore().doubleValue())
            .add(studentScore.getMathScore().doubleValue())
            .divide(3.0)
            .getResult(),
        examFailStudentResponse.getAverageScore());
  }
}
