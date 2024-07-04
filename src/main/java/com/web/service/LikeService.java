package com.web.service;

import com.web.domain.Board;
import com.web.domain.User;

public interface LikeService {
    boolean isUserLikedBoard(User user, Board board);
    void toggleLike(User user, Board board);
}