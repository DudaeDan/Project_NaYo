package com.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.web.domain.Answer;
import com.web.domain.Board;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.TmpBoard;
import com.web.domain.User;
import com.web.repository.AdminAnswerRepository;
import com.web.repository.AdminBoardRepository;
import com.web.repository.AdminInquiryRepository;
import com.web.repository.AdminNoticeRepository;
import com.web.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminUserRepository adUserRepo;
	@Autowired
	private AdminBoardRepository adBoardRepo;
	@Autowired
	private AdminNoticeRepository adNoticeRepo;
	@Autowired
	private AdminInquiryRepository adInquiryRepo;
	@Autowired
	private AdminAnswerRepository adAnswerRepo;

	// 로그인
	@Override
	public User adminLogin(String userId, String userPw) {
		return adUserRepo.findByUserIdAndUserPw(userId, userPw);
	}

	// admin main
	@Override
	public List<User> getMainUserList() {
		return adUserRepo.findTop6ByOrderByUserNumberDesc();
	}

	@Override
//	public List<Board> getMainBoardList() {
	public List<TmpBoard> getMainBoardList() {
		return adBoardRepo.findTop6ByOrderByBoardNumberDesc();
	}

	@Override
	public List<Notice> getMainNoticeList() {
		return adNoticeRepo.findTop6ByOrderByNoticeNumberDesc();
	}

	@Override
	public List<Inquiry> getMainInquiryList() {
//		return adInquiryRepo.findTop6ByOrderByInquiryNumberDesc();
		return adInquiryRepo.findTop6ByOrderByInquiryProgressAsc();
	}

	// admin member
	@Override
	public Page<User> getMemberList(Pageable pageable) {
		return adUserRepo.findAllByOrderByUserNumberDesc(pageable);
	}

	@Override
	public Page<User> getMemberList(Pageable pageable, String searchType, String searchKeyword) {
		if ("name".equals(searchType)) {
			return adUserRepo.findByUserNameContainingOrderByUserNumberDesc(searchKeyword, pageable);
		} else if ("id".equals(searchType)) {
			return adUserRepo.findByUserIdContainingOrderByUserNumberDesc(searchKeyword, pageable);
		} else {
			return adUserRepo.findAllByOrderByUserNumberDesc(pageable);
		}
	}

	@Override
	public User getMember(Long userNumber) {
		return adUserRepo.findById(userNumber).orElse(null);
	}

	// admin board
//	@Override
//	public Page<Board> getBoardList(Pageable pageable) {
//		return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
//	}
	@Override
	public Page<TmpBoard> getBoardList(Pageable pageable) {
		return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
	}

//	@Override
//	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchKeyword) {
//		if ("title".equals(searchType)) {
//			return adBoardRepo.findByBoardTitleContainingOrderByBoardNumberDesc(searchKeyword, pageable);
//		} else if ("nickname".equals(searchType)) {
//			return adBoardRepo.findByUserUserNicknameContainingOrderByBoardNumberDesc(searchKeyword, pageable);
//		} else {
//			return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
//		}
//	}
	@Override
	public Page<TmpBoard> getBoardList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adBoardRepo.findByBoardTitleContainingOrderByBoardNumberDesc(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adBoardRepo.findByUserUserNicknameContainingOrderByBoardNumberDesc(searchKeyword, pageable);
		} else {
			return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
		}
	}

//	@Override
//	public Board getBoard(Long boardNumber) {
//		return adBoardRepo.findById(boardNumber).orElse(null);
//	}
	@Override
	public TmpBoard getBoard(Long boardNumber) {
		return adBoardRepo.findById(boardNumber).orElse(null);
	}

//	@Override
//	public void modifyBoard(Long boardNumber, String boardTitle, String boardContent) {
////		Board board = adBoardRepo.findById(boardNumber).orElse(null);
//		TmpBoard board = adBoardRepo.findById(boardNumber).orElse(null);
//		board.setBoardTitle(boardTitle);
////		board.setBoardContent(boardContent);
//		adBoardRepo.save(board);
//	}
	@Override
	public void modifyBoard(TmpBoard tmpBoard) {
		TmpBoard board = adBoardRepo.findById(tmpBoard.getBoardNumber()).orElse(null);
		board.setBoardTitle(tmpBoard.getBoardTitle());
		board.setBoardIngredient(tmpBoard.getBoardIngredient());
		board.setBoardContent1(tmpBoard.getBoardContent1());
		board.setBoardContent2(tmpBoard.getBoardContent2());
		board.setBoardContent3(tmpBoard.getBoardContent3());
		board.setBoardContent4(tmpBoard.getBoardContent4());
		board.setBoardContent5(tmpBoard.getBoardContent5());
		board.setBoardContent6(tmpBoard.getBoardContent6());
		board.setBoardContent7(tmpBoard.getBoardContent7());
		board.setBoardContent8(tmpBoard.getBoardContent8());
		board.setBoardContent9(tmpBoard.getBoardContent9());
		board.setBoardContent10(tmpBoard.getBoardContent10());
		adBoardRepo.save(board);
	}

	@Override
	public void deleteBoard(Long boardNumber) {
		adBoardRepo.deleteById(boardNumber);
	}

	// admin notice
	@Override
	public Page<Notice> getNoticeList(Pageable pageable) {
		return adNoticeRepo.findAllByOrderByNoticeNumberDesc(pageable);
	}

	@Override
	public Page<Notice> getNoticeList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adNoticeRepo.findByNoticeTitleContainingOrderByNoticeNumberDesc(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adNoticeRepo.findByUserUserNicknameContainingOrderByNoticeNumberDesc(searchKeyword, pageable);
		} else {
			return adNoticeRepo.findAllByOrderByNoticeNumberDesc(pageable);
		}
	}

	@Override
	public Notice getNotice(Long noticeNumber) {
		return adNoticeRepo.findById(noticeNumber).orElse(null);
	}

	@Override
	public void writeNotice(Long userNumber, String noticeTitle, String noticeContent) {
		User user = adUserRepo.findById(userNumber).orElse(null);
		Notice notice = new Notice();
		notice.setUserNumber(user.getUserNumber());
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		adNoticeRepo.save(notice);
	}

	@Override
	public void modifyNotice(Long noticeNumber, String noticeTitle, String noticeContent) {
		Notice notice = adNoticeRepo.findById(noticeNumber).orElse(null);
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		adNoticeRepo.save(notice);
	}

	@Override
	public void deleteNotice(Long noticeNumber) {
		adNoticeRepo.deleteById(noticeNumber);
	}

	// admin Inquiry

	@Override
	public Page<Inquiry> getInquiryList(Pageable pageable) {
//		return adInquiryRepo.findAllByOrderByInquiryNumberDesc(pageable);
		return adInquiryRepo.findAllByOrderByInquiryProgressAsc(pageable);
	}

	@Override
	public Page<Inquiry> getInquiryList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
//			return adInquiryRepo.findByInquiryTitleContainingOrderByInquiryNumberDesc(searchKeyword, pageable);
			return adInquiryRepo.findByInquiryTitleContainingOrderByInquiryProgressAsc(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
//			return adInquiryRepo.findByUserUserNicknameContainingOrderByInquiryNumberDesc(searchKeyword, pageable);
			return adInquiryRepo.findByUserUserNicknameContainingOrderByInquiryProgressAsc(searchKeyword, pageable);
		} else {
//			return adInquiryRepo.findAllByOrderByInquiryNumberDesc(pageable);
			return adInquiryRepo.findAllByOrderByInquiryProgressAsc(pageable);
		}
	}

	@Override
	public Inquiry getInquiry(Long inquiryNumber) {
		return adInquiryRepo.findById(inquiryNumber).orElse(null);
	}

	@Override
	public Answer getAnswer(Long inquiryNumber) {
		return adAnswerRepo.findById(inquiryNumber).orElse(null);
	}

	@Override
	public void addinquiryAnswer(Long inquiryNumber, Long userNumber, String answerContent) {
		Inquiry inquiry = adInquiryRepo.findById(inquiryNumber).orElse(null);
		User user = adUserRepo.findById(userNumber).orElse(null);
		Answer answer = new Answer();
		answer.setUserNumber(user.getUserNumber());
		answer.setInquiryNumber(inquiry.getInquiryNumber());
		answer.setAnswerContent(answerContent);
		adAnswerRepo.save(answer);
	}

	@Override
	public void AnswerDone(Long inquiryNumber) {
		Inquiry inquiry = adInquiryRepo.findById(inquiryNumber).orElse(null);
		if (adAnswerRepo.findById(inquiryNumber) != null) {
			inquiry.setInquiryProgress(1L);
			adInquiryRepo.save(inquiry);
		}
	}
}
