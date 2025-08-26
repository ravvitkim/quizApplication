package com.quiz.quiz.controller;


import com.quiz.quiz.dto.MemberDto;
import com.quiz.quiz.dto.PlayDto;
import com.quiz.quiz.service.PlayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlayController {
    private final PlayService playService;
    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @GetMapping("/playList")
    public String playList(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginEmail");
        if (!"root".equals(loginId)) {
            return "redirect:/";
        }
        List<PlayDto> list = playService.getAllList();
        model.addAttribute("list", list);
        return "playMember";
    }

    @PostMapping("/play/save")
    public String savePlay(@RequestParam Long totalAnswerTrue,
                           @RequestParam Long totalAnswerFalse,
                           HttpSession session) {
        String memberId = (String) session.getAttribute("loginEmail");

        PlayDto dto = new PlayDto();
        dto.setMemberId(memberId);
        dto.setTotalAnswerTrue(totalAnswerTrue);
        dto.setTotalAnswerFalse(totalAnswerFalse);

        playService.savePlay(dto);

        return "redirect:/playMember";
    }




}
