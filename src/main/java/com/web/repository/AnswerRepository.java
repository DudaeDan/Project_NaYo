package com.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Answer findByInquiryNumber(Long inquiryNumber);


	
}
