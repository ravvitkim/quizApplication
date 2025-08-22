package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Play;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayDto {
    private Long id;
    private String memberId; // Member의 id값을 직접 문자열로 저장
    private LocalDateTime dateTime;
    private Long totalAnswerTrue=0L;  // 지금까지 누적 맞은 개수
    private Long totalAnswerFalse=0L; // 지금까지 누적 틀린 개수

    public static PlayDto fromPlayEntity(Play play) {
        return new PlayDto(
          play.getId(),
          play.getMemberId(),
          play.getDateTime(),
          play.getTotalAnswerTrue(),
          play.getTotalAnswerFalse()
        );
    }

    public static Play toDto(PlayDto dto) {
        Play play = new Play();
        play.setId(dto.getId());
        play.setMemberId(dto.getMemberId());
        play.setDateTime(dto.getDateTime());
        play.setTotalAnswerTrue(dto.getTotalAnswerTrue());
        play.setTotalAnswerFalse(dto.getTotalAnswerFalse());
        return play;
    }
}
