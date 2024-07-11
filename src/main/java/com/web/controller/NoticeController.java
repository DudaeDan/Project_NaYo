package com.web.controller;

import com.web.domain.Notice;
import com.web.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String listNotices(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeDate"));
        Page<Notice> noticePage = noticeService.findAllNotices(pageRequest);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("totalItems", noticePage.getTotalElements());

        return "notice/notice_list";
    }

    @GetMapping("/view/{id}")
    public String viewNotice(@PathVariable("id") Long id, Model model) {
        Notice notice = noticeService.findNoticeById(id);
        if (notice == null) {
            return "redirect:/notice/list";
        }
        model.addAttribute("notice", notice);
        return "notice/notice_view";
    }
}