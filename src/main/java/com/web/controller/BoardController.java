package com.web.controller;

import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.User;
import com.web.service.BoardService;
import com.web.service.CommentService;
import com.web.service.LikeService;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    public String listBoards(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "boardDate"));
        Page<Board> boardPage = boardService.findAllBoards(pageRequest);

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());
        model.addAttribute("totalItems", boardPage.getTotalElements());

        return "board/boardlist";
    }

    @GetMapping("/ranking")
    public String rankingBoards(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "30") int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "boardLike"));
        Page<Board> boardPage = boardService.findAllBoards(pageRequest);
        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());
        return "/board/ranking";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            model.addAttribute("WriteSession", true);
            return "login/login";
        }
        model.addAttribute("board", new Board());
        return "/board/boardinsert";
    }

    @PostMapping("/save")
    public String saveBoard(@ModelAttribute Board board,
                            @RequestParam("mainImgFile") MultipartFile mainImgFile,
                            @RequestParam("stepDescriptions") List<String> stepDescriptions,
                            @RequestParam("stepImages") List<MultipartFile> stepImages,
                            @RequestParam("ingredientNames") List<String> ingredientNames,
                            @RequestParam("ingredientAmounts") List<String> ingredientAmounts,
                            HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null) {
            board.setUser(loggedInUser);
            boardService.saveBoard(board, mainImgFile, stepDescriptions, stepImages, ingredientNames, ingredientAmounts);
        } else {
            return "redirect:/login/login";
        }
        return "redirect:/board/list";
    }

    @GetMapping("/view/{id}")
    public String viewBoard(@PathVariable Long id, Model model, HttpSession session) {
        Board board = boardService.findBoardById(id);
        if (board != null) {
            board.setBoardHit(board.getBoardHit() + 1);
            boardService.save(board);

            model.addAttribute("board", board);
            model.addAttribute("steps", board.getSteps());
            model.addAttribute("ingredients", board.getIngredients());

            User user = (User) session.getAttribute("user");
            boolean isLiked = false;
            if (user != null) {
                isLiked = likeService.isUserLikedBoard(user, board);
            }
            model.addAttribute("isLiked", isLiked);

            // 댓글 목록 추가
            List<Comments> comments = commentService.findCommentsByBoard(board);
            model.addAttribute("comments", comments);

            // 베스트 댓글 계산
            int maxLikes = comments.stream().mapToInt(Comments::getLikes).max().orElse(0);
            if (maxLikes > 0) {
                List<Comments> bestComments = comments.stream()
                        .filter(comment -> comment.getLikes() == maxLikes)
                        .collect(Collectors.toList());
                model.addAttribute("bestComments", bestComments);
            } else {
                model.addAttribute("bestComments", null);
            }
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

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String deleteBoard(@PathVariable Long id, @RequestParam("userPw") String userPw, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null && loggedInUser.getUserPw().equals(userPw)) {
            boardService.deleteBoardWithFiles(id);
            return "deleted";
        }
        return "incorrectPassword";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        Board board = boardService.findBoardById(id);
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null && board != null && loggedInUser.getUserNumber().equals(board.getUser().getUserNumber())) {
            model.addAttribute("board", board);
            model.addAttribute("steps", board.getSteps());
            model.addAttribute("ingredients", board.getIngredients());
            return "/board/boardedit";
        }
        return "redirect:/board/list";
    }

    @PostMapping("/update/{id}")
    public String updateBoard(@PathVariable Long id,
                              @ModelAttribute Board board,
                              @RequestParam("mainImgFile") MultipartFile mainImgFile,
                              @RequestParam("stepDescriptions") List<String> stepDescriptions,
                              @RequestParam("stepImages") List<MultipartFile> stepImages,
                              @RequestParam("ingredientNames") List<String> ingredientNames,
                              @RequestParam("ingredientAmounts") List<String> ingredientAmounts,
                              @RequestParam("existingMainImg") String existingMainImg,
                              @RequestParam("existingStepImages") List<String> existingStepImages,
                              HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/login/login";
        }

        Board existingBoard = boardService.findBoardById(id);
        if (existingBoard == null || !loggedInUser.getUserNumber().equals(existingBoard.getUser().getUserNumber())) {
            return "redirect:/login/login";
        }

        boardService.updateBoard(id, board, mainImgFile, stepDescriptions, stepImages, ingredientNames, ingredientAmounts, existingMainImg, existingStepImages);
        return "redirect:/board/view/" + id;
    }
}