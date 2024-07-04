package com.web.controller;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.service.BoardService;
import com.web.service.LikeService;
import com.web.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public String listBoards(Model model) {
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "/board/boardlist";
    }

    @GetMapping("/ranking")
    public String ranking() {

        return "/board/ranking";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            // 로그인되지 않은 경우 alert 메시지와 함께 로그인 페이지로 이동
            model.addAttribute("WriteSession", true);
            return "login/login";
        }
        model.addAttribute("board", new Board());
        return "/board/boardinsert";
    }
    
    @PostMapping("/save")
    public String saveBoard(@ModelAttribute Board board,
                            @RequestParam("mainImgFile") MultipartFile mainImgFile,
                            @RequestParam("stepDescription") List<String> stepDescriptions,
                            @RequestParam("stepImage") List<MultipartFile> stepImages,
                            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null) {
            board.setUser(loggedInUser);
            boardService.saveBoard(board, mainImgFile, stepDescriptions, stepImages);
        } else {
            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
            return "redirect:/login/login";
        }
        return "redirect:/board/list";
    }

    @GetMapping("/view/{id}")
    public String viewBoard(@PathVariable Long id, Model model, HttpSession session) {
        Board board = boardService.findBoardById(id);
        if (board != null) {
            // 조회수 증가
            board.setBoardHit(board.getBoardHit() + 1);
            boardService.save(board);

            model.addAttribute("board", board);
            model.addAttribute("steps", board.getSteps());

            User user = (User) session.getAttribute("user"); // 로그인된 유저
            boolean isLiked = false;
            if (user != null) {
                isLiked = likeService.isUserLikedBoard(user, board);
            }
            model.addAttribute("isLiked", isLiked);
        }
        return "/board/boardview";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public String toggleLike(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "notLoggedIn";
        }

        Board board = boardService.findBoardById(id);
        if (board != null) {
            likeService.toggleLike(user, board);
            boolean isLiked = likeService.isUserLikedBoard(user, board);
            return isLiked ? "liked" : "unliked";
        }
        return "error";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board/list";
    }
}