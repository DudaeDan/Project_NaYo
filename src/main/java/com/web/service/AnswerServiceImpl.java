package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.Answer;
import com.web.repository.AnswerRepository;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Override
	public Answer findAnswerByInquiryNumber(Long inquiryNumber) {
		 return answerRepository.findByInquiryNumber(inquiryNumber);
	}
	
	

}
