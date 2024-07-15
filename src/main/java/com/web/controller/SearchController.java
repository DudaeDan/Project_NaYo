package com.web.controller;

import com.web.domain.Board;
import com.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

   @Autowired
   private SearchService searchService;

    @GetMapping
    public String search(@RequestParam("type") String type, @RequestParam("keyword") String keyword, Model model) {
        List<Board> results = searchService.search(type, keyword);
        model.addAttribute("boards", results);
        return "board/searchlist"; // 검색 결과를 보여줄 뷰 페이지
    }
}
