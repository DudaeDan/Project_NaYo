package com.web.repository;

import com.web.domain.CommentLike;
import com.web.domain.Comments;
import com.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserAndComment(User user, Comments comment);
    boolean existsByUserAndComment(User user, Comments comment);
}