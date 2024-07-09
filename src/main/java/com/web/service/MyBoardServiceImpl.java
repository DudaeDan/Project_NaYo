package com.web.service;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.repository.CommentLikeRepository;
import com.web.repository.CommentRepository;
import com.web.repository.IngredientRepository;
import com.web.repository.LikeRepository;
import com.web.repository.MyBoardRepository;
import com.web.repository.MyUserRepository;
import com.web.repository.ReplyLikeRepository;
import com.web.repository.ReplyRepository;
import com.web.repository.StepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyBoardServiceImpl implements MyBoardService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyBoardRepository myBoardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Override
    public void updateUser(User user) {
        myUserRepository.save(user);
    }
    @Override
    public List<Board> findPostsByUserNumber(Long userNumber) {
        return myBoardRepository.findByUser_UserNumber(userNumber);
    }



    @Override
    @Transactional
    public void deleteUser(Long userNumber) {
        // ReplyLike 삭제
        replyLikeRepository.deleteByUser_UserNumber(userNumber);

        // Reply 삭제
        replyRepository.deleteByUser_UserNumber(userNumber);

        // CommentLike 삭제
        commentLikeRepository.deleteByUser_UserNumber(userNumber);

        // Comment 삭제
        commentRepository.deleteByUser_UserNumber(userNumber);

        // Board와 관련된 Ingredients 삭제
        myBoardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
            ingredientRepository.deleteByBoard_BoardNumber(board.getBoardNumber());
        });

        // Board와 관련된 Steps 삭제
        myBoardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
            stepRepository.deleteByBoard_BoardNumber(board.getBoardNumber());
        });

        // Like 삭제
        likeRepository.deleteByUser_UserNumber(userNumber);

        // Board 삭제
        myBoardRepository.deleteByUser_UserNumber(userNumber);

        // User 삭제
        myUserRepository.deleteByUserNumber(userNumber);
    }

    @Override
    public boolean isNicknameAvailable(String nickname) {
        return myUserRepository.existsByUserNickname(nickname);
    }
}