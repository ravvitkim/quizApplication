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
        System.out.println("Play 저장 요청 dto = " + dto);

        if (dto.getTotalAnswerTrue() == null) {
            dto.setTotalAnswerTrue(0L);
        }
        if (dto.getTotalAnswerFalse() == null) {
            dto.setTotalAnswerFalse(0L);
        }

        List<Play> existingPlays = repository.findByMemberId(dto.getMemberId());
        Play play;
        if (existingPlays.isEmpty()) {
            // 기존 기록 없으면 새로 생성
            play = new Play();
            play.setMemberId(dto.getMemberId());
            play.setTotalAnswerTrue(0L);
            play.setTotalAnswerFalse(0L);
        } else {
            play = existingPlays.get(existingPlays.size() - 1);
        }
        play.setTotalAnswerTrue(play.getTotalAnswerTrue() + dto.getTotalAnswerTrue());
        play.setTotalAnswerFalse(play.getTotalAnswerFalse() + dto.getTotalAnswerFalse());
        play.setDateTime(LocalDateTime.now());
        Play saved = repository.save(play);
        return PlayDto.fromPlayEntity(saved);
    }


}
