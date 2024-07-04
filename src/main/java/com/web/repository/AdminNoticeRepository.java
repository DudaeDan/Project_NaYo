package com.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Notice;

public interface AdminNoticeRepository extends JpaRepository<Notice, Long> {

	List<Notice> findTop6ByOrderByNoticeNumberDesc();

	Page<Notice> findAllByOrderByNoticeNumberDesc(Pageable pageable);

	Page<Notice> findByNoticeTitleContainingOrderByNoticeNumberDesc(String noticeTitle, Pageable pageable);

	Page<Notice> findByUserUserNicknameContainingOrderByNoticeNumberDesc(String userNickname, Pageable pageable);

}
