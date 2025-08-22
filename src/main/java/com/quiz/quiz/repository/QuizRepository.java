package com.quiz.quiz.repository;

import com.quiz.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query(value = "SELECT * FROM quiz ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Quiz findRandomQuiz();

}
