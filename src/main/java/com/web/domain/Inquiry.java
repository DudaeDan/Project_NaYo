package com.web.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_INQUIRY")
public class Inquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Inquiry_SEQ")
	@SequenceGenerator(name = "Inquiry_SEQ", sequenceName = "Inquiry_SEQ", allocationSize = 1)
	private Long inquiryNumber;

	@Column(nullable = false)
	private String inquiryTitle;

	@Column(nullable = false)
	private String inquiryContent;

	@Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
	private Date inquiryDate;

	@Column(insertable = false, columnDefinition = "NUMBER DEFAULT 0")
	private Long inquiryProgress;

	@Column(name = "user_number", nullable = false)
	private Long userNumber;

	@ManyToOne
	@JoinColumn(name = "user_number", insertable = false, updatable = false)
	private User user;

}
