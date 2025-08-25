package com.quiz.quiz.service;

import com.quiz.quiz.dto.MemberDto;
import com.quiz.quiz.dto.PlayDto;
import com.quiz.quiz.entity.Member;
import com.quiz.quiz.entity.Play;
import com.quiz.quiz.repository.MemberRepository;
import com.quiz.quiz.repository.PlayRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        Play play = PlayDto.toDto(dto);
        play.setDateTime(LocalDateTime.now());
        Play saved = repository.save(play);
        return PlayDto.fromPlayEntity(saved);
//20250825 이 코드 확인해봐야함 디비에 저장안됨;;;;;;;;;;;;
    }

}
