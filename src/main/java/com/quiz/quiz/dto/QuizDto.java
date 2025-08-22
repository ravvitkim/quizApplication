package com.quiz.quiz.dto;

import com.quiz.quiz.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    @Size(min = 1,max = 50,message = "공백일 수 없습니다")
    private String content;
    private String master;
    @NotNull(message = "정답을 선택해주세요.")
    private Boolean answer;

    public static QuizDto fromQuizEntity(Quiz quiz) {
        return new QuizDto(
                quiz.getId(),
                quiz.getContent(),
                quiz.getMaster(),
                quiz.getAnswer()
        );
    }

    public static Quiz toDto(QuizDto dto) {
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setContent(dto.getContent());
        quiz.setMaster(dto.getMaster());
        quiz.setAnswer(dto.getAnswer());
        return quiz;
    }


}
