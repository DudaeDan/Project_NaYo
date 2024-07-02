package com.web.controller;

import com.web.domain.Board;
import com.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boardlist")
    public String boardlist(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 8); // 페이지 크기를 8로 설정
        Page<Board> boardPage = boardService.getBoardList(pageable);
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "border/boardlist";
    }
    
    @GetMapping("layout")
    public String getMethodName() {
    	return "layout";
    }
    
}
