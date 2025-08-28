package com.quiz.quiz.repository;

import com.quiz.quiz.entity.Play;
import com.quiz.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayRepository extends JpaRepository<Play, Long> {
    List<Play> findByMemberId(String memberId);
    Optional<Play> findTopByMemberIdOrderByDateTimeDesc(String memberId);
}
