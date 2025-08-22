package com.quiz.quiz.service;

import com.quiz.quiz.dto.QuizDto;
import com.quiz.quiz.entity.Quiz;
import com.quiz.quiz.repository.MemberRepository;
import com.quiz.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository repository;

    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    public List<QuizDto> getAllList() {
        List<Quiz> memberList = repository.findAll();
        System.out.println(memberList);
        return memberList
                .stream()
                .map(x -> QuizDto.fromQuizEntity(x))
                .toList();
    }

    public void updateQuiz(QuizDto dto) {
        Quiz quiz = repository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("퀴즈가 없습니다."));
        quiz.setContent(dto.getContent());
        quiz.setMaster(dto.getMaster());
        quiz.setAnswer(dto.getAnswer());
        repository.save(quiz);
    }


    public void insertQuiz(QuizDto dto) {
        Quiz quiz = QuizDto.toDto(dto);
        repository.save(quiz);
        System.out.println("==============");
        System.out.println(quiz);
    }

    public void deleteQuiz(Long id) {
        repository.deleteById(id);
    }

    public QuizDto findQuiz(Long updateId) {
        Quiz quiz = repository.findById(updateId).orElse(null);
        if (ObjectUtils.isEmpty(quiz)) {
            return null;
        } else {
            return QuizDto.fromQuizEntity(quiz);
        }
    }

    public QuizDto getRandomQuiz() {
        Quiz quiz = repository.findRandomQuiz();
        if (ObjectUtils.isEmpty(quiz)) {
            return null;
        }
        return QuizDto.fromQuizEntity(quiz);
    }

    public boolean checkAnswer(Long quizId, Boolean answer) {
        Quiz quiz = repository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈를 찾을 수 없습니다."));
        return quiz.getAnswer().equals(answer);
    }
}

