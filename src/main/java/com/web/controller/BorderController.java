package com.web.controller;

import com.web.service.BoardService;
import com.web.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BorderController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/borderlist")
    public String borderlist(Model model, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 8); // 기본 페이지 크기를 8로 설정
        }
        Page<Board> boardPage = boardService.getBoardList(pageable);
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        return "border/borderlist";
    }
}
