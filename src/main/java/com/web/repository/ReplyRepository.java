package com.web.repository;

import com.web.domain.Comments;
import com.web.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentOrderByLikesDesc(Comments comment);
    List<Reply> findByComment(Comments comment);

}