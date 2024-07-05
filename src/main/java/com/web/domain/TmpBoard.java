package com.web.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_TMP_board")
public class TmpBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
	@SequenceGenerator(name = "board_seq", sequenceName = "BOARD_SEQ", allocationSize = 1)
	@Column(name = "board_number")
	private Long boardNumber;

	@Column(nullable = false)
	private String boardTitle;

	@Column(nullable = false)
	private String boardIngredient;

	@Column(name = "board_content_1")
	private String boardContent1;

	@Column(name = "board_content_2")
	private String boardContent2;

	@Column(name = "board_content_3")
	private String boardContent3;

	@Column(name = "board_content_4")
	private String boardContent4;

	@Column(name = "board_content_5")
	private String boardContent5;

	@Column(name = "board_content_6")
	private String boardContent6;

	@Column(name = "board_content_7")
	private String boardContent7;

	@Column(name = "board_content_8")
	private String boardContent8;

	@Column(name = "board_content_9")
	private String boardContent9;

	@Column(name = "board_content_10")
	private String boardContent10;

	@Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
	private Date boardDate;

	@Column(insertable = false, columnDefinition = "NUMBER DEFAULT 0")
	private int boardHit;

	@Column(name = "user_number", nullable = false)
	private Long userNumber;

	@ManyToOne
	@JoinColumn(name = "user_number", insertable = false, updatable = false)
	private User user;

}
