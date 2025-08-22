package com.quiz.quiz.service;

import com.quiz.quiz.repository.MemberRepository;
import com.quiz.quiz.repository.PlayRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayService {
    private final PlayRepository repository;
    public PlayService(PlayRepository repository) {
        this.repository = repository;
    }
}
