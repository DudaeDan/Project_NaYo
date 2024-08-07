package com.web.repository;

import com.web.domain.CommentLike;
import com.web.domain.Comments;
import com.web.domain.Reply;
import com.web.domain.ReplyLike;
import com.web.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserAndComment(User user, Comments comment);
    boolean existsByUserAndComment(User user, Comments comment);
    List<CommentLike> findByComment(Comments comment);

	void deleteByComment_Id(Long id);
	void deleteByUser_UserNumber(Long userNumber);
	void deleteByComment(Comments comment);

}