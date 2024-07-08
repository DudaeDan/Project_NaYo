package com.web.service;

import com.web.domain.Reply;
import com.web.domain.ReplyLike;
import com.web.domain.Comments;
import com.web.domain.User;
import com.web.repository.ReplyRepository;
import com.web.repository.ReplyLikeRepository;
import com.web.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public boolean addReply(Long commentId, String content, User user) {
        Comments comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) return false;

        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setUser(user);
        reply.setContent(content);
        reply.setLikes(0);
        replyRepository.save(reply);
        return true;
    }

    @Override
    public void toggleLike(User user, Reply reply) {
        ReplyLike existingLike = replyLikeRepository.findByUserAndReply(user, reply);
        if (existingLike != null) {
            replyLikeRepository.delete(existingLike);
            reply.setLikes(reply.getLikes() - 1);
        } else {
            ReplyLike newLike = new ReplyLike();
            newLike.setUser(user);
            newLike.setReply(reply);
            replyLikeRepository.save(newLike);
            reply.setLikes(reply.getLikes() + 1);
        }
        replyRepository.save(reply);
    }

    @Override
    public boolean isUserLikedReply(User user, Reply reply) {
        return replyLikeRepository.findByUserAndReply(user, reply) != null;
    }

    @Override
    @Transactional
    public Reply findReplyById(Long id) {
        return replyRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }
}