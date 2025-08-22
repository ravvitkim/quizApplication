package com.quiz.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // PK, 자동 증가

    @Column(nullable = false)
    private String content;  // 퀴즈 내용

    @Column(nullable = false)
    private String master;

    @Column(nullable = false)
    private Boolean answer;
}
