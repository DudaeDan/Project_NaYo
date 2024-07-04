package com.web.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.web.domain.Answer;
import com.web.domain.Board;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.TmpBoard;
import com.web.domain.User;

public interface AdminService {

	// 로그인
	User adminLogin(String userId, String userPw);

	// 메인
	List<User> getMainUserList();

//	List<Board> getMainBoardList();
	List<TmpBoard> getMainBoardList();

	List<Notice> getMainNoticeList();

	List<Inquiry> getMainInquiryList();

	// 멤버
	Page<User> getMemberList(Pageable pageable);

	Page<User> getMemberList(Pageable pageable, String searchType, String searchKeyword);

	User getMember(Long userNumber);

	// 게시판
//	Page<Board> getBoardList(Pageable pageable);
//	
//	Page<Board> getBoardList(Pageable pageable, String searchType, String searchKeyword);
//	
//	Board getBoard(Long boardNumber);
	Page<TmpBoard> getBoardList(Pageable pageable);

	Page<TmpBoard> getBoardList(Pageable pageable, String searchType, String searchKeyword);

	TmpBoard getBoard(Long boardNumber);

//	void modifyBoard(Long boardNumber, String boardTitle, String boardContent);
	void modifyBoard(TmpBoard tmpBoard);

	void deleteBoard(Long boardNumber);

	// 공지
	Page<Notice> getNoticeList(Pageable pageable);

	Page<Notice> getNoticeList(Pageable pageable, String searchType, String searchKeyword);

	Notice getNotice(Long noticeNumber);

	void writeNotice(Long userNumber, String noticeTitle, String noticeContent);

	void modifyNotice(Long noticeNumber, String noticeTitle, String noticeContent);

	void deleteNotice(Long noticeNumber);

	// 문의

	Page<Inquiry> getInquiryList(Pageable pageable);

	Page<Inquiry> getInquiryList(Pageable pageable, String searchType, String searchKeyword);

	Inquiry getInquiry(Long inquiryNumber);

	// 답변

	Answer getAnswer(Long inquiryNumber);

	void addinquiryAnswer(Long inquiryNumber, Long userNumber, String answerContent);

	void AnswerDone(Long inquiryNumber);
}
