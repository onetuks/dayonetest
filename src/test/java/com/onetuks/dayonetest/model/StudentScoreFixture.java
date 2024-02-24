package com.onetuks.dayonetest.model;

public class StudentScoreFixture {

  public static StudentScore passed() {
    return StudentScore.builder()
        .exam("기말고사")
        .studentName("합격자")
        .koreanScore(90)
        .englishScore(80)
        .mathScore(100)
        .build();
  }

  public static StudentScore failed() {
    return StudentScore.builder()
        .exam("기말고사")
        .studentName("낙제자")
        .koreanScore(40)
        .englishScore(60)
        .mathScore(50)
        .build();
  }
}
