package com.web.service;

import java.util.List;

import com.web.domain.Inquiry;

public interface InquiryService {

	List<Inquiry> findInquiriesByUserNumber(Long userNumber);

	void createInquiry(Inquiry inquiry);

	Inquiry findInquiryById(Long inquiryNumber);

}
