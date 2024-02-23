package com.onetuks.dayonetest.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveExamScoreRequest {

    private final String studentName;
    private final int koreanScore;
    private final int englishScore;
    private final int mathScore;

}
