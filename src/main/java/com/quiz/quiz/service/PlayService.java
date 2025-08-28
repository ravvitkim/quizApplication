package com.quiz.quiz.service;

import com.quiz.quiz.dto.PlayDto;
import com.quiz.quiz.entity.Play;
import com.quiz.quiz.repository.PlayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlayService {
    private final PlayRepository repository;
    public PlayService(PlayRepository repository) {
        this.repository = repository;
    }

    public List<PlayDto> getAllList() {
        List<Play> entities = repository.findAll();
        return entities.stream()
                .map(PlayDto::fromPlayEntity)
                .toList();
    }



    public PlayDto savePlay(PlayDto dto) {
        Play play = new Play();
        play.setMemberId(dto.getMemberId());
        play.setTotalAnswerTrue(dto.getTotalAnswerTrue()); // 이번 결과만
        play.setTotalAnswerFalse(dto.getTotalAnswerFalse());
        play.setDateTime(LocalDateTime.now());

        return PlayDto.fromPlayEntity(repository.save(play));
    }


    public List<Play> getAllByMemberId(String memberId) {
        return repository.findByMemberId(memberId);
    }

}
