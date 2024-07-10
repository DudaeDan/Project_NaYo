package com.web.domain;

import java.sql.Date;

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
@Table(name = "NY_refrigerator")
public class Refrigerator {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refrigerator_SEQ")
	@SequenceGenerator(name = "refrigerator_SEQ", sequenceName = "refrigerator_SEQ", allocationSize = 1)
	@Column(name = "refrigerator_number", nullable = false)
	private Long refrigeratorNumber;

	@Column(name = "user_number",nullable = false)
	private Long userNumber;

	@Column(nullable = false)
	private Long refIngredientNumber;

	private Date expDate;

	@ManyToOne
	@JoinColumn(name = "user_number", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "refIngredientNumber", insertable = false, updatable = false)
	private RefrigeratorIngredient refrigeratorIngredient;

}
