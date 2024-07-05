package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Answer;

public interface AdminAnswerRepository extends JpaRepository<Answer, Long> {

	Object findByInquiryNumber(Long inquiryNumber);

}
