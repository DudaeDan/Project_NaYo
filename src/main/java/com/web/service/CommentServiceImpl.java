package com.web.service;

import com.web.domain.Comments;
import com.web.domain.CommentLike;
import com.web.domain.Board;
import com.web.domain.User;
import com.web.repository.CommentRepository;
import com.web.repository.CommentLikeRepository;
import com.web.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public List<Comments> findCommentsByBoard(Board board) {
        return commentRepository.findByBoardOrderByLikesDesc(board);
    }

    @Override
    @Transactional
    public Comments saveComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comments comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            replyRepository.deleteAll(comment.getReplies());
            commentRepository.delete(comment);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Comments comment) {
        replyRepository.deleteAll(comment.getReplies());
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public boolean isCommentLikedByUser(User user, Comments comment) {
        return commentLikeRepository.existsByUserAndComment(user, comment);
    }

    @Override
    @Transactional
    public boolean addCommentLike(User user, Comments comment) {
        if (!commentLikeRepository.existsByUserAndComment(user, comment)) {
            CommentLike commentLike = new CommentLike();
            commentLike.setUser(user);
            commentLike.setComment(comment);
            commentLikeRepository.save(commentLike);
            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeCommentLike(User user, Comments comment) {
        if (commentLikeRepository.existsByUserAndComment(user, comment)) {
            CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment);
            if (commentLike != null) {
                commentLikeRepository.delete(commentLike);
                comment.setLikes(comment.getLikes() - 1);
                commentRepository.save(comment);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Comments findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void toggleLike(User user, Comments comment) {
        CommentLike existingLike = commentLikeRepository.findByUserAndComment(user, comment);
        if (existingLike != null) {
            commentLikeRepository.delete(existingLike);
            comment.setLikes(comment.getLikes() - 1);
        } else {
            CommentLike newLike = new CommentLike();
            newLike.setUser(user);
            newLike.setComment(comment);
            commentLikeRepository.save(newLike);
            comment.setLikes(comment.getLikes() + 1);
        }
        commentRepository.save(comment);
    }

    @Override
    public boolean isUserLikedComment(User user, Comments comment) {
        return commentLikeRepository.findByUserAndComment(user, comment) != null;
    }
}