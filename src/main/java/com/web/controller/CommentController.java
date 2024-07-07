package com.web.controller;

import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.User;
import com.web.service.BoardService;
import com.web.service.CommentService;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public String addComment(@RequestParam Long boardId,
                             @RequestParam String content,
                             HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return "notLoggedIn";
        }

        Board board = boardService.findBoardById(boardId);
        if (board != null) {
            Comments comment = new Comments();
            comment.setBoard(board);
            comment.setUser(loggedInUser);
            comment.setContent(content);
            comment.setLikes(0);
            commentService.saveComment(comment);
            return "success";
        }
        return "error";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public String toggleCommentLike(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "notLoggedIn";
        }

        Comments comment = commentService.findCommentById(id);
        if (comment != null) {
            commentService.toggleLike(user, comment);
            boolean isLiked = commentService.isUserLikedComment(user, comment);
            return isLiked ? "liked" : "unliked";
        }
        return "error";
    }

    @PostMapping("/delete/{commentId}")
    @ResponseBody
    public String deleteComment(@PathVariable Long commentId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return "notLoggedIn";
        }

        Comments comment = commentService.findCommentById(commentId);
        if (comment != null && comment.getUser().getUserNumber().equals(loggedInUser.getUserNumber())) {
            commentService.deleteComment(comment);
            return "deleted";
        }
        return "error";
    }
    
    @GetMapping("/list")
    public String listComments(@RequestParam Long boardId, Model model, HttpSession session) {
        Board board = boardService.findBoardById(boardId);
        List<Comments> comments = commentService.findCommentsByBoard(board);
        User loggedInUser = (User) session.getAttribute("user");

        // 좋아요가 0 이상인 댓글들을 필터링하여 좋아요 순으로 정렬합니다.
        List<Comments> bestComments = comments.stream()
                .filter(comment -> comment.getLikes() > 0)
                .sorted(Comparator.comparingInt(Comments::getLikes).reversed())
                .collect(Collectors.toList());

        // 좋아요가 같은 댓글들을 모두 베스트 댓글로 포함합니다.
        if (!bestComments.isEmpty()) {
            int maxLikes = bestComments.get(0).getLikes();
            bestComments = bestComments.stream()
                    .filter(comment -> comment.getLikes() == maxLikes)
                    .collect(Collectors.toList());
        }

        model.addAttribute("comments", comments);
        model.addAttribute("bestComments", bestComments.isEmpty() ? null : bestComments);

        if (loggedInUser != null) {
            Map<Long, Boolean> commentLikes = new HashMap<>();

            for (Comments comment : comments) {
                commentLikes.put(comment.getId(), commentService.isCommentLikedByUser(loggedInUser, comment));
            }

            model.addAttribute("commentLikes", commentLikes);
        }

        return "comments/comment_list";
    }
}