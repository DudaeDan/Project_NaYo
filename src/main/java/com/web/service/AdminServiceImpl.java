package com.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.web.domain.Board;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.User;
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

	// 테스트

	@Override
	public User getLoginUser(User user) {
		Optional<User> findUser = adUserRepo.findByUserId(user.getUserId());
		if (findUser.isPresent()) { 
			return findUser.get();
		} else {
			return null;
		}
	}

	// admin main
	@Override
	public List<User> getMainUserList() {
		return adUserRepo.findTop6ByOrderByUserNumberDesc();
	}

	@Override
	public List<Board> getMainBoardList() {
		return adBoardRepo.findTop6ByOrderByBoardNumberDesc();
	}

	@Override
	public List<Notice> getMainNoticeList() {
		return adNoticeRepo.findTop6ByOrderByNoticeNumberDesc();
	}

	@Override
	public List<Inquiry> getMainInquiryList() {
		return adInquiryRepo.findTop6ByOrderByInquiryNumberDesc();
	}

	// admin member
	@Override
	public Page<User> getMemberList(Pageable pageable) {
		return adUserRepo.findAllByOrderByUserNumberDesc(pageable);
	}

	@Override
	public Page<User> getMemberList(Pageable pageable, String searchType, String searchKeyword) {
		if ("name".equals(searchType)) {
			return adUserRepo.findByUserNameContaining(searchKeyword, pageable);
		} else if ("id".equals(searchType)) {
			return adUserRepo.findByUserIdContaining(searchKeyword, pageable);
		} else {
			return adUserRepo.findAllByOrderByUserNumberDesc(pageable);
		}
	}

	@Override
	public User getMember(Long userNumber) {
		return adUserRepo.findById(userNumber).orElse(null);
	}

	// admin board
	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
	}

	@Override
	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adBoardRepo.findByBoardTitleContaining(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adBoardRepo.findByUserUserNicknameContaining(searchKeyword, pageable);
		} else {
			return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
		}
	}

	@Override
	public Board getBoard(Long boardNumber) {
		// TODO Auto-generated method stub
		return adBoardRepo.findById(boardNumber).orElse(null);
	}

	@Override
	public void modifyBoard(Long boardNumber, String boardTitle, String boardContent) {
		Board board = adBoardRepo.findById(boardNumber).orElse(null);
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
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
			return adNoticeRepo.findByNoticeTitleContaining(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adNoticeRepo.findByUserUserNicknameContaining(searchKeyword, pageable);
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
		notice.setUser(user);
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
		return adInquiryRepo.findAllByOrderByInquiryNumberDesc(pageable);
	}

	@Override
	public Page<Inquiry> getInquiryList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adInquiryRepo.findByInquiryTitleContaining(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adInquiryRepo.findByUserUserNicknameContaining(searchKeyword, pageable);
		} else {
			return adInquiryRepo.findAllByOrderByInquiryNumberDesc(pageable);
		}
	}

	@Override
	public Inquiry getInquiry(Long inquiryNumber) {
		return adInquiryRepo.findById(inquiryNumber).orElse(null);
	}

}
