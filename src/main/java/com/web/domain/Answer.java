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
@Table(name = "NY_ANSWER")
public class Answer {

	@Id
	@Column(name = "inquiry_number", nullable = false)
	private Long inquiryNumber;

	@Column(nullable = false)
	private String answerContent;

	@Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
	private Date answerDate;

	@Column(name = "user_number", nullable = false)
	private Long userNumber;

	@ManyToOne
	@JoinColumn(name = "user_number", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "inquiry_number", insertable = false, updatable = false)
	private Inquiry inquiry;

	public void update(String answerContent) {
		this.answerContent = answerContent;
	}

}


