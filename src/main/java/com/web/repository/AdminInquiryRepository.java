package com.web.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.web.domain.Inquiry;

public interface AdminInquiryRepository extends JpaRepository<Inquiry, Long> {

//	List<Inquiry> findTop6ByOrderByInquiryNumberDesc();
	List<Inquiry> findTop6ByOrderByInquiryProgressAsc();

//	Page<Inquiry> findAllByOrderByInquiryNumberDesc(Pageable pageable);
	Page<Inquiry> findAllByOrderByInquiryProgressAsc(Pageable pageable);

//	Page<Inquiry> findByInquiryTitleContainingOrderByInquiryNumberDesc(String noticeTitle, Pageable pageable);
	Page<Inquiry> findByInquiryTitleContainingOrderByInquiryProgressAsc(String noticeTitle, Pageable pageable);

//	Page<Inquiry> findByUserUserNicknameContainingOrderByInquiryNumberDesc(String userNickname, Pageable pageable);
	Page<Inquiry> findByUserUserNicknameContainingOrderByInquiryProgressAsc(String userNickname, Pageable pageable);

}
