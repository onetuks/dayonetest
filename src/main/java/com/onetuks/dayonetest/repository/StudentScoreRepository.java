package com.onetuks.dayonetest.repository;

import com.onetuks.dayonetest.model.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {}
