package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "INGREDIENT_SEQ", allocationSize = 1)
    @Column(name="ingredient_id")
    private Long ingredientId;

    @ManyToOne
    @JoinColumn(name = "board_number", nullable = false)
    private Board board;

    @Column(name = "ingredient_name", length = 255, nullable = false)
    private String ingredientName;

    @Column(name = "ingredient_quantity", length = 255, nullable = false)
    private String ingredientQuantity;
}