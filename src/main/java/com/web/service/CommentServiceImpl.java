package com.web.service;

import com.web.domain.Comments;
import com.web.domain.Reply;
import com.web.domain.ReplyLike;
import com.web.domain.CommentLike;
import com.web.domain.Board;
import com.web.domain.User;
import com.web.repository.CommentRepository;
import com.web.repository.ReplyLikeRepository;
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
    
    @Autowired
    private ReplyLikeRepository replyLikeRepository;

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
            // 댓글과 연결된 좋아요 레코드 삭제
            List<CommentLike> likes = commentLikeRepository.findByComment(comment);
            for (CommentLike like : likes) {
                commentLikeRepository.delete(like);
            }
            commentRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Comments comment) {
        // 댓글과 연결된 답글과 좋아요 레코드 삭제
        List<Reply> replies = replyRepository.findByComment(comment);
        for (Reply reply : replies) {
            // 답글에 연결된 좋아요 삭제
            List<ReplyLike> replyLikes = replyLikeRepository.findByReply(reply);
            for (ReplyLike replyLike : replyLikes) {
                replyLikeRepository.delete(replyLike);
            }
            replyRepository.delete(reply);
        }
        // 댓글에 연결된 좋아요 삭제
        List<CommentLike> likes = commentLikeRepository.findByComment(comment);
        for (CommentLike like : likes) {
            commentLikeRepository.delete(like);
        }
        // 댓글 삭제
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
    
//    @Override
//    public List<Comments> findCommentsByUserNumber(Long userNumber) {
//    	return commentRepository.findByUser_UserNumber(userNumber);
//    }
    
    @Override
    public List<Comments> findCommentsByUserNumber(Long userNumber) {
    	return commentRepository.findByUserNumberOrderByboardNumberDesc(userNumber);
    }
    
}