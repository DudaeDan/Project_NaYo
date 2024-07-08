package com.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="NY_refrigerator_ingredient")
public class RefrigeratorIngredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refrigerator_INGREDIENT_SEQ")
	@SequenceGenerator(name = "refrigerator_INGREDIENT_SEQ", sequenceName = "refrigerator_INGREDIENT_SEQ", allocationSize = 1)
	private Long refIngredientNumber;
	
	@Column(nullable = false, name="ingredient_category")
	private String refIngredientCategory;

	@Column(nullable = false, name="ingredient_NAME")
	private String refIngredientName;
	

}
