package com.web.controller;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.service.BoardService;
import com.web.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/toggle")
    @ResponseBody
    public boolean toggleLike(@RequestParam Long boardId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null) {
            Board board = boardService.findBoardById(boardId);
            if (board != null) {
                likeService.toggleLike(loggedInUser, board);
                return likeService.isUserLikedBoard(loggedInUser, board);
            }
        }
        return false;
    }
    
    
    
    
    
}