package com.web.repository;


import com.web.domain.Reply;
import com.web.domain.ReplyLike;
import com.web.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    ReplyLike findByUserAndReply(User user, Reply reply);
    boolean existsByUserAndReply(User user, Reply reply);
    List<ReplyLike> findByReply(Reply reply);

}