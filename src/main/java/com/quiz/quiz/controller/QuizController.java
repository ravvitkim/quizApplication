package com.quiz.quiz.controller;

import com.quiz.quiz.dto.PlayDto;
import com.quiz.quiz.dto.QuizDto;
import com.quiz.quiz.entity.Quiz;
import com.quiz.quiz.service.PlayService;
import com.quiz.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService service;

    @Autowired
    PlayService playService;

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("title", "퀴즈 목록");
        List<QuizDto> quizList = service.getAllList();
        model.addAttribute("quiz", quizList);
        return "quiz/quizList";
    }

    @GetMapping("/insertForm")
    public String insertFormView(Model model) {
//        insertForm에 껍데기 DTO를 보낸다
        model.addAttribute("dto", new QuizDto());
        return "quiz/insertForm";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("dto") QuizDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "quiz/insertForm";
        }
        System.out.println(dto);
        service.insertQuiz(dto);
        return "redirect:/quiz";
    }

    @GetMapping("{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        QuizDto dto = service.findQuiz(id);
        model.addAttribute("quiz", dto);
        return "quiz/updateForm";
    }


    @PostMapping("/update")
    public String updateQuiz(@Valid @ModelAttribute("quiz") QuizDto quizDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("quiz", quizDto);
            return "quiz/updateForm";
        }
        service.updateQuiz(quizDto);
        return "redirect:/quiz";
    }


    @PostMapping("/delete")
    public String deleteQuiz(@RequestParam("id") Long id) {
        service.deleteQuiz(id);
        return "redirect:/quiz";
    }

    @GetMapping("/play")
    public String playQuiz(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Boolean status = (Boolean) session.getAttribute("loginStatus");

        if (status == null || !status) {
            redirectAttributes.addFlashAttribute("errorMessage", "승인된 사용자만 퀴즈를 플레이할 수 있습니다.");
            return "redirect:/login";
        }

        model.addAttribute("quiz", service.getRandomQuiz());
        return "quiz/play";
    }




    @PostMapping("/check")
    public String checkAnswer(@RequestParam("quizId") Long quizId,
                              @RequestParam("answer") Boolean answer,
                              HttpSession session,
                              Model model) {
        boolean result = service.checkAnswer(quizId, answer);
        model.addAttribute("result", result);

        // 로그인한 회원 ID 가져오기
        String memberId = (String) session.getAttribute("loginEmail");
        if (memberId != null) {
            // 기존 플레이 기록 조회
            List<PlayDto> playList = playService.getAllList();

            // 누적값 계산 (없으면 0으로 초기화)
            long totalTrue = playList.stream()
                    .mapToLong(p -> p.getTotalAnswerTrue() != null ? p.getTotalAnswerTrue() : 0L)
                    .sum();
            long totalFalse = playList.stream()
                    .mapToLong(p -> p.getTotalAnswerFalse() != null ? p.getTotalAnswerFalse() : 0L)
                    .sum();

            // 이번 결과를 누적해서 저장
            PlayDto newPlay = new PlayDto();
            newPlay.setMemberId(memberId);
            newPlay.setTotalAnswerTrue(totalTrue + (result ? 1 : 0));
            newPlay.setTotalAnswerFalse(totalFalse + (result ? 0 : 1));
            playService.savePlay(newPlay);
        }

        return "quiz/result";
    }






}
