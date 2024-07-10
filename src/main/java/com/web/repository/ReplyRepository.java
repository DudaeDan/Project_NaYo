package com.web.repository;

import com.web.domain.Comments;
import com.web.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentOrderByLikesDesc(Comments comment);
    List<Reply> findByComment(Comments comment);
    void deleteByUser_UserNumber(Long userNumber);
    
    List<Reply> findByUser_UserNumber(Long userNumber);
    List<Reply> findByComment_Id(Long commentId);

}