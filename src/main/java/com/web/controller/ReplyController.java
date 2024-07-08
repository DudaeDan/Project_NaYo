package com.web.controller;

import com.web.domain.Reply;
import com.web.domain.User;
import com.web.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/add")
    @ResponseBody
    public String addReply(@RequestParam Long commentId,
                           @RequestParam String content,
                           HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return "notLoggedIn";
        }

        return replyService.addReply(commentId, content, loggedInUser) ? "success" : "error";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public String toggleReplyLike(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "notLoggedIn";
        }

        Reply reply = replyService.findReplyById(id);
        if (reply != null) {
            replyService.toggleLike(user, reply);
            boolean isLiked = replyService.isUserLikedReply(user, reply);
            return isLiked ? "liked" : "unliked";
        }
        return "error";
    }

    @PostMapping("/delete/{replyId}")
    @ResponseBody
    public String deleteReply(@PathVariable Long replyId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return "notLoggedIn";
        }

        Reply reply = replyService.findReplyById(replyId);
        if (reply != null && reply.getUser().getUserNumber().equals(loggedInUser.getUserNumber())) {
            replyService.deleteReply(reply);
            return "deleted";
        }
        return "error";
    }
}