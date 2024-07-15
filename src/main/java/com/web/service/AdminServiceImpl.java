package com.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.domain.Answer;
import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.Inquiry;
import com.web.domain.Notice;
import com.web.domain.Reply;
import com.web.domain.User;
import com.web.repository.AdminAnswerRepository;
import com.web.repository.AdminBoardRepository;
import com.web.repository.AdminInquiryRepository;
import com.web.repository.AdminNoticeRepository;
import com.web.repository.AdminUserRepository;
import com.web.repository.BoardRepository;
import com.web.repository.CommentLikeRepository;
import com.web.repository.CommentRepository;
import com.web.repository.IngredientRepository;
import com.web.repository.LikeRepository;
import com.web.repository.ReplyLikeRepository;
import com.web.repository.ReplyRepository;
import com.web.repository.StepRepository;
import com.web.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private static final String MAIN_IMG_DIR = "src/main/resources/static/Images/border/main/";
	private static final String STEP_IMG_DIR = "src/main/resources/static/Images/border/step/";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReplyLikeRepository replyLikeRepository;
	@Autowired
	private CommentLikeRepository commentLikeRepository;
	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private StepRepository stepRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private FileService fileService;

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
		User user = adUserRepo.findByUserIdAndUserPw(userId, userPw);
		if (user != null && user.getUserPw().equals(userPw)) {
			return user;
		}
		return null;
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

	@Override
	@Transactional
	public void deleteMember(Long userNumber) {
		User user = userRepository.findById(userNumber).orElse(null);
		if (user != null) {
			// 유저가 남긴 좋아요 삭제 (댓글, 답글, 게시글 등)
			replyLikeRepository.deleteByUser_UserNumber(userNumber);
			commentLikeRepository.deleteByUser_UserNumber(userNumber);
			likeRepository.deleteByUser_UserNumber(userNumber);

			// 유저가 작성한 답글 삭제
			replyRepository.findByUser_UserNumber(userNumber).forEach(reply -> {
				replyLikeRepository.deleteByReply_Id(reply.getId());
				replyRepository.delete(reply);
			});

			// 유저가 작성한 댓글 삭제
			commentRepository.findByUser_UserNumber(userNumber).forEach(comment -> {
				replyRepository.findByComment_Id(comment.getId()).forEach(reply -> {
					replyLikeRepository.deleteByReply_Id(reply.getId());
					replyRepository.delete(reply);
				});
				commentLikeRepository.deleteByComment_Id(comment.getId());
				commentRepository.delete(comment);
			});

			// 유저가 작성한 게시글의 답글 삭제
			boardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
				commentRepository.findByBoard_BoardNumber(board.getBoardNumber()).forEach(comment -> {
					replyRepository.findByComment_Id(comment.getId()).forEach(reply -> {
						replyLikeRepository.deleteByReply_Id(reply.getId());
						replyRepository.delete(reply);
					});
				});
			});

			// 유저가 작성한 게시글의 댓글 삭제
			boardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
				commentRepository.findByBoard_BoardNumber(board.getBoardNumber()).forEach(comment -> {
					commentLikeRepository.deleteByComment_Id(comment.getId());
					commentRepository.delete(comment);
				});
			});

			// 유저가 작성한 게시글의 스텝과 재료 삭제
			boardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
				board.getSteps().forEach(step -> {
					fileService.deleteFile(step.getStepImage(), STEP_IMG_DIR);
					stepRepository.delete(step);
				});

				board.getIngredients().forEach(ingredient -> {
					ingredientRepository.delete(ingredient);
				});
			});

			// 유저가 작성한 게시글 삭제
			boardRepository.findByUser_UserNumber(userNumber).forEach(board -> {
				fileService.deleteFile(board.getMainImg(), MAIN_IMG_DIR);
				boardRepository.delete(board);
			});

			// 유저 정보 삭제
			userRepository.delete(user);
		}
	}

	// admin board
	@Override
	public Page<Board> getBoardList(Pageable pageable) {
		return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
	}

	@Override
	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adBoardRepo.findByBoardTitleContainingOrderByBoardNumberDesc(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adBoardRepo.findByUserUserNicknameContainingOrderByBoardNumberDesc(searchKeyword, pageable);
		} else {
			return adBoardRepo.findAllByOrderByBoardNumberDesc(pageable);
		}
	}

	@Override
	public Board getBoard(Long boardNumber) {
		Board board = adBoardRepo.findById(boardNumber).orElse(null);
		if (board != null) {
			// Fetch comments and replies
			List<Comments> comments = board.getComments();
			for (Comments comment : comments) {
				List<Reply> replies = comment.getReplies(); // 댓글의 답글을 로드
				comment.setReplies(replies); // 댓글 객체에 답글 세팅
			}
		}
		return board;
	}

	@Override
	public void modifyBoard(Board board) {
		Board existingBoard = adBoardRepo.findById(board.getBoardNumber()).orElse(null);
		if (existingBoard != null) {
			existingBoard.setBoardTitle(board.getBoardTitle());
			existingBoard.setBoardContent(board.getBoardContent());
			adBoardRepo.save(existingBoard);
		}
	}

    @Override
    @Transactional
    public void deleteBoardWithFiles(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null) {
            // 이미지 파일 삭제
            fileService.deleteFile(board.getMainImg(), MAIN_IMG_DIR);
            System.out.println("===========================메인 이미지 삭제");
            board.getSteps().forEach(step -> fileService.deleteFile(step.getStepImage(), STEP_IMG_DIR));
            System.out.println("===========================스텝 이미지 삭제");

            // ----------------여기서 오류----------------------
            // 댓글 및 관련된 답글, 좋아요 삭제
            board.getComments().forEach(comment -> {
                // 답글 및 답글 좋아요 삭제
                comment.getReplies().forEach(reply -> {
                    replyLikeRepository.deleteAll(replyLikeRepository.findByReply(reply));
                    System.out.println("===========================답글 좋아요 삭제");
                    replyRepository.delete(reply);
                    System.out.println("===========================답글 삭제");
                });

                // 댓글 좋아요 삭제
                commentLikeRepository.deleteAll(commentLikeRepository.findByComment(comment));
                System.out.println("===========================댓글 좋아요 삭제");
                // 댓글 삭제
                commentRepository.delete(comment);
                System.out.println("===========================댓글 삭제");
            });

            // 보드 삭제
            boardRepository.delete(board);
            System.out.println("===========================게시글 삭제");
        }
    }

	@Override
	@Transactional
	public void deleteComment(Long id) {
		Comments comment = commentRepository.findById(id).orElse(null);
		if (comment != null) {
			// 댓글 좋아요 삭제
			commentLikeRepository.deleteByComment(comment);

			// 댓글의 답글과 답글 좋아요 삭제
			for (Reply reply : comment.getReplies()) {
				replyLikeRepository.deleteByReply(reply);
				replyRepository.delete(reply);
			}

			// 댓글 삭제
			commentRepository.delete(comment);
		}
	}

	@Override
	@Transactional
	public void deleteReply(Long id) {
		Reply reply = replyRepository.findById(id).orElse(null);
		if (reply != null) {
			// 답글 좋아요 삭제
			replyLikeRepository.deleteByReply(reply);

			// 답글 삭제
			replyRepository.delete(reply);
		}
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
		return adInquiryRepo.findAllByOrderByInquiryProgressAsc(pageable);
	}

	@Override
	public Page<Inquiry> getInquiryList(Pageable pageable, String searchType, String searchKeyword) {
		if ("title".equals(searchType)) {
			return adInquiryRepo.findByInquiryTitleContainingOrderByInquiryProgressAsc(searchKeyword, pageable);
		} else if ("nickname".equals(searchType)) {
			return adInquiryRepo.findByUserUserNicknameContainingOrderByInquiryProgressAsc(searchKeyword, pageable);
		} else {
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
	public void addInquiryAnswer(Long inquiryNumber, Long userNumber, String answerContent) {
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