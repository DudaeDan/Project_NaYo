package com.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_search_recipe")
public class SearchRecipe {

	@Id
	@Column(insertable = false, updatable = false)
	private Long recipeNumber;

	@Column(insertable = false, updatable = false)
	private String recipeName;

	@Column(insertable = false, updatable = false)
	private String recipeIngredient;
	
	@Column(insertable = false, updatable = false)
	private String recipeTitle;

}
