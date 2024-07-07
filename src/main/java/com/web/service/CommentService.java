package com.web.service;

import com.web.domain.Comments;
import com.web.domain.Board;
import com.web.domain.User;

import java.util.List;

public interface CommentService {
    List<Comments> findCommentsByBoard(Board board);
    Comments saveComment(Comments comment);
    void deleteComment(Long id);
    void deleteComment(Comments comment);
    boolean isCommentLikedByUser(User user, Comments comment);
    Comments findCommentById(Long id);
    void toggleLike(User user, Comments comment);
    boolean isUserLikedComment(User user, Comments comment);
    boolean addCommentLike(User user, Comments comment);
    boolean removeCommentLike(User user, Comments comment);
}