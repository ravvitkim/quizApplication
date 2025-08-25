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
    private String memberId;
    private LocalDateTime dateTime;
    private Long totalAnswerTrue;
    private Long totalAnswerFalse;
}

