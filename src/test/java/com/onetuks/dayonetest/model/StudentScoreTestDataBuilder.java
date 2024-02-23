package com.onetuks.dayonetest.model;

public class StudentScoreTestDataBuilder {

    public static StudentScore.StudentScoreBuilder passed() {
        return StudentScore.builder()
                .koreanScore(80)
                .englishScore(100)
                .mathScore(90)
                .studentName("홍길동")
                .exam("중간고사");
    }

    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore.builder()
                .koreanScore(50)
                .englishScore(40)
                .mathScore(30)
                .studentName("낙제자")
                .exam("중간고사");
    }
}
