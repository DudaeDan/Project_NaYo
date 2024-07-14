package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.domain.Board;
import com.web.service.BoardService;

@Controller
public class HomeController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        List<Board> bestBoards = boardService.findBestBoards(); // 좋아요 순
        List<Board> recentBoards = boardService.findRecentBoards(); // 최신순
        List<Board> weeklyBestBoards = boardService.findWeeklyBestBoards(); // 금주의 베스트

        model.addAttribute("bestBoards", bestBoards);
        model.addAttribute("recentBoards", recentBoards);
        model.addAttribute("weeklyBestBoards", weeklyBestBoards);

        return "Index";
    }
	
	
}
