package com.onetuks.dayonetest.service;

import com.onetuks.dayonetest.MyCalculator;
import com.onetuks.dayonetest.controller.response.ExamFailStudentResponse;
import com.onetuks.dayonetest.controller.response.ExamPassStudentResponse;
import com.onetuks.dayonetest.model.StudentFail;
import com.onetuks.dayonetest.model.StudentPass;
import com.onetuks.dayonetest.model.StudentScore;
import com.onetuks.dayonetest.repository.StudentFailRepository;
import com.onetuks.dayonetest.repository.StudentPassRepository;
import com.onetuks.dayonetest.repository.StudentScoreRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer koreanScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .exam(exam)
                .studentName(studentName)
                .koreanScore(koreanScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        MyCalculator myCalculator = new MyCalculator();
        Double averageScore = myCalculator
                .add(koreanScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (averageScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .averageScore(averageScore)
                    .build();

            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .averageScore(averageScore)
                    .build();

            studentFailRepository.save(studentFail);
        }
    }

    public List<ExamPassStudentResponse> getPassStudentsList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter(pass -> Objects.equals(pass.getExam(), exam))
                .filter(pass -> pass.getAverageScore() >= 60)
                .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAverageScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentsList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter(fail -> Objects.equals(fail.getExam(), exam))
                .filter(fail -> fail.getAverageScore() < 60)
                .map(fail -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAverageScore()))
                .toList();
    }

}
