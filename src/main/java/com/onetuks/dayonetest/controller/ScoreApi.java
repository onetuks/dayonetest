package com.onetuks.dayonetest.controller;

import com.onetuks.dayonetest.controller.request.SaveExamScoreRequest;
import com.onetuks.dayonetest.controller.response.ExamFailStudentResponse;
import com.onetuks.dayonetest.controller.response.ExamPassStudentResponse;
import com.onetuks.dayonetest.service.StudentScoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScoreApi {

    private final StudentScoreService studentScoreService;

    @PutMapping(path = "/exam/{exam}/score")
    public void save(
            @PathVariable("exam") String exam,
            @RequestBody SaveExamScoreRequest request
    ) {
        studentScoreService.saveScore(
                request.getStudentName(),
                exam,
                request.getKoreanScore(),
                request.getEnglishScore(),
                request.getMathScore()
        );
    }

    @GetMapping(path = "/exam/{exam}/pass")
    public List<ExamPassStudentResponse> pass(
            @PathVariable("exam") String exam
    ) {
        return studentScoreService.getPassStudentsList(exam);
    }

    @GetMapping(path = "/exam/{exam}/fail")
    public List<ExamFailStudentResponse> fail(
            @PathVariable("exam") String exam
    ) {
        return studentScoreService.getFailStudentsList(exam);
    }

}
