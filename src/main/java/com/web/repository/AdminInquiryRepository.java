package com.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Inquiry;
import com.web.domain.Notice;

public interface AdminInquiryRepository extends JpaRepository<Inquiry, Long>{
	
	List<Inquiry> findTop6ByOrderByInquiryNumberDesc();
	
	Page<Inquiry> findAllByOrderByInquiryNumberDesc(Pageable pageable);

	Page<Inquiry> findByInquiryTitleContaining(String noticeTitle, Pageable pageable);

	Page<Inquiry> findByUserUserNicknameContaining(String userNickname, Pageable pageable);

}
