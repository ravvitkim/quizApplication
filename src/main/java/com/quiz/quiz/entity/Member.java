package com.quiz.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "member")
public class Member {
    @Id
    private String id;
    private String password;
    @Column(nullable = false)
    private boolean status;
}
