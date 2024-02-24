package com.onetuks.dayonetest.model;

import com.onetuks.dayonetest.MyCalculator;

public class StudentPassFixture {

  public static StudentPass create(StudentScore studentScore) {
    MyCalculator calculator = new MyCalculator();

    return StudentPass.builder()
        .exam(studentScore.getExam())
        .studentName(studentScore.getStudentName())
        .averageScore(
            calculator
                .add(studentScore.getKoreanScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult())
        .build();
  }

  public static StudentPass create(String studentName, String exam) {
    return StudentPass.builder().studentName(studentName).exam(exam).averageScore(70.0).build();
  }
}
