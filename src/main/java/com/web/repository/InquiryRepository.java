package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	List<Inquiry> findByUserNumber(Long userNumber);
	
}
