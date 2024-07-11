package com.web.service;

import com.web.domain.Answer;

public interface AnswerService {

	Answer findAnswerByInquiryNumber(Long inquiryNumber);

}
