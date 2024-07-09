package com.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Board;
import com.web.domain.TmpBoard;

//public interface AdminBoardRepository extends JpaRepository<Board, Long> {
//
//	List<Board> findTop6ByOrderByBoardNumberDesc();
//
//	Page<Board> findAllByOrderByBoardNumberDesc(Pageable pageable);
//
//	Page<Board> findByBoardTitleContainingOrderByBoardNumberDesc(String boardTitle, Pageable pageable);
//
//	Page<Board> findByUserUserNicknameContainingOrderByBoardNumberDesc(String userNickname, Pageable pageable);
//
//}
public interface AdminBoardRepository extends JpaRepository<Board, Long> {
	
    List<Board> findTop6ByOrderByBoardNumberDesc();

    Page<Board> findByBoardTitleContainingOrderByBoardNumberDesc(String boardTitle, Pageable pageable);

    Page<Board> findByUserUserNicknameContainingOrderByBoardNumberDesc(String userNickname, Pageable pageable);

    Page<Board> findAllByOrderByBoardNumberDesc(Pageable pageable);
}
