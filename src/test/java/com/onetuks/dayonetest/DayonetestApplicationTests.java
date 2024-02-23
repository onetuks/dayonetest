package com.onetuks.dayonetest;

import com.onetuks.dayonetest.model.StudentScore;
import com.onetuks.dayonetest.model.StudentScoreFixture;
import com.onetuks.dayonetest.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DayonetestApplicationTests extends IntegrationTest {

    @Autowired
    private StudentScoreRepository studentScoreRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void contextLoads() {
        StudentScore passed = StudentScoreFixture.passed();
        StudentScore savedStudentScore = studentScoreRepository.save(passed);

        entityManager.flush();
        entityManager.clear();

        studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

        Assertions.assertAll("studentScore",
                () -> Assertions.assertEquals(savedStudentScore.getId(), passed.getId()),
                () -> Assertions.assertEquals(savedStudentScore.getStudentName(), passed.getStudentName()),
                () -> Assertions.assertEquals(savedStudentScore.getKoreanScore(), passed.getKoreanScore()),
                () -> Assertions.assertEquals(savedStudentScore.getEnglishScore(), passed.getEnglishScore()),
                () -> Assertions.assertEquals(savedStudentScore.getMathScore(), passed.getMathScore())
        );
    }

}
