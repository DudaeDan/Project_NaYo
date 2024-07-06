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

    @Column(name = "ingredient_name", nullable = false)
    private String name;

    @Column(name = "ingredient_amount", nullable = false)
    private String amount;

    // No-argument constructor
    public Ingredient() {}

    // Constructor
    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }
}