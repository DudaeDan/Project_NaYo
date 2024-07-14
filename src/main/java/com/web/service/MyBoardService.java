package com.web.service;

import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.User;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MyBoardService {
    List<Board> findPostsByUserNumber(Long userNumber);
    void updateUser(User user);
    void deleteUser(Long userNumber);
    boolean isNicknameAvailable(String nickname);
    List<Board> findBoardsByUserComments(Long userNumber);
    List<Comments> findCommentsByUserNumber(Long userNumber);
    Page<Board> findPostsByUserNumber(Long userNumber, PageRequest pageRequest);

    
    
}
