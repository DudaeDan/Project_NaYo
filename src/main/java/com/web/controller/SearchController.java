package com.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.domain.Inquiry;

@Controller
@RequestMapping("search")
public class SearchController {
	
	@GetMapping("/")
    public String mainPage() {
        return "index";
    }

//    @GetMapping("/search")
//    public String search(@RequestParam String keyword, Model model) {
//        List<Inquiry> searchResults = inquiryService.searchInquiries(keyword);
//        searchResults.sort(Comparator.comparing(Inquiry::getLikes).reversed()); // 좋아요 순으로 정렬
//        model.addAttribute("inquiries", searchResults);
//        return "search_results";
//    }

	
}
