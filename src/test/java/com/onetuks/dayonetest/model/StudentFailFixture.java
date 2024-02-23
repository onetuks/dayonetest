package com.onetuks.dayonetest.model;

import com.onetuks.dayonetest.MyCalculator;

public class StudentFailFixture {

    public static StudentFail create(StudentScore studentScore) {
        MyCalculator calculator = new MyCalculator();

        return StudentFail.builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .averageScore(calculator
                        .add(studentScore.getKoreanScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();
    }

    public static StudentFail create(String studentName, String exam) {
        return StudentFail.builder()
                .studentName(studentName)
                .exam(exam)
                .averageScore(50.0)
                .build();
    }

}
