package com.web.service;

import com.web.domain.Board;
import com.web.domain.Comments;
import com.web.domain.Reply;
import com.web.domain.Step;
import com.web.domain.User;
import com.web.repository.BoardRepository;
import com.web.repository.CommentLikeRepository;
import com.web.repository.CommentRepository;
import com.web.repository.IngredientRepository;
import com.web.repository.LikeRepository;
import com.web.repository.ReplyRepository;
import com.web.repository.StepRepository;
import com.web.repository.UserRepository;
import com.web.repository.MyUserRepository;
import com.web.repository.RefrigeratorIngredientRepository;
import com.web.repository.RefrigeratorRepository;
import com.web.repository.ReplyLikeRepository;
import com.web.repository.MyBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserServiceImpl implements MyUserService {

	@Autowired
	private MyUserRepository myUserRepository;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyLikeRepository replyLikeRepository;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private RefrigeratorIngredientRepository refrigeratorIngredientRepository;


    private static final String MAIN_IMG_DIR = "src/main/resources/static/Images/border/main/";
    private static final String STEP_IMG_DIR = "src/main/resources/static/Images/border/step/";

    @Override
    public void updateUser(User user) {
        myUserRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userNumber) {
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

            // 유저의 냉장고 재료 삭제
            refrigeratorRepository.findByUser_UserNumber(userNumber).forEach(refrigerator -> {
                refrigeratorRepository.delete(refrigerator);
            });

            // 유저 정보 삭제
            userRepository.delete(user);
        }
    }
    

    //닉네임 중복 확인
    @Override
    public boolean isNicknameAvailable(String nickname) {
        return !myUserRepository.existsByUserNickname(nickname);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}