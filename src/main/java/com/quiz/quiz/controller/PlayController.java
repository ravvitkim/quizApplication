package com.quiz.quiz.controller;


import com.quiz.quiz.service.PlayService;
import org.springframework.stereotype.Controller;

@Controller
public class PlayController {
    private final PlayService playService;
    public PlayController(PlayService playService) {
        this.playService = playService;
    }



}
