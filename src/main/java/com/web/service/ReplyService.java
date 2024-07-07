package com.web.service;

import com.web.domain.Reply;
import com.web.domain.User;

public interface ReplyService {
    boolean addReply(Long commentId, String content, User user);
    void toggleLike(User user, Reply reply);
    boolean isUserLikedReply(User user, Reply reply);
    Reply findReplyById(Long id);
    void deleteReply(Reply reply);
}