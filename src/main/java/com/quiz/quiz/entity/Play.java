package com.quiz.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "play")
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId; // Member의 id값을 직접 문자열로 저장
    private LocalDateTime dateTime;
    private Long totalAnswerTrue;  // 지금까지 누적 맞은 개수
    private Long totalAnswerFalse; // 지금까지 누적 틀린 개수
}

