package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Inquiry;
import com.web.repository.InquiryRepository;


@Service
public class InquiryServiceImpl implements InquiryService {

	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Override
	public List<Inquiry> findInquiriesByUserNumber(Long userNumber) {
		return inquiryRepository.findByUserNumber(userNumber);
	}
	
	@Override
	public void createInquiry(Inquiry inquiry) {
		 inquiryRepository.save(inquiry);
	}
	
	@Override
	public Inquiry findInquiryById(Long inquiryNumber) {
		 return inquiryRepository.findById(inquiryNumber).orElse(null);
	}
}
