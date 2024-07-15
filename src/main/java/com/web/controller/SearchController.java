package com.web.controller;

import com.web.domain.Board;
import com.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

<<<<<<< HEAD
   @Autowired
   private SearchService searchService;

    @GetMapping
=======
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("test")
>>>>>>> 2442d04db1f51ce1e0601b54b16aa6c11fc816f7
    public String search(@RequestParam("type") String type, @RequestParam("keyword") String keyword, Model model) {
        List<Board> results = searchService.search(type, keyword);
        model.addAttribute("boards", results);
        return "board/searchlist"; // 검색 결과를 보여줄 뷰 페이지
    }
}

