package com.web.service;

import com.web.domain.Board;
import com.web.domain.Like;
import com.web.domain.User;
import com.web.repository.BoardRepository;
import com.web.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public boolean isUserLikedBoard(User user, Board board) {
        return likeRepository.findByUserAndBoard(user, board) != null;
    }

    @Override
    public void toggleLike(User user, Board board) {
        Like existingLike = likeRepository.findByUserAndBoard(user, board);
        if (existingLike != null) {
            // 좋아요가 이미 눌러진 상태라면 좋아요 취소
            likeRepository.delete(existingLike);
            board.setBoardLike(board.getBoardLike() - 1);
        } else {
            // 좋아요 추가
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setBoard(board);
            likeRepository.save(newLike);
            board.setBoardLike(board.getBoardLike() + 1);
        }
        boardRepository.save(board);
    }
}