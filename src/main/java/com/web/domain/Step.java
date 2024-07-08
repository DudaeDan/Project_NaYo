package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_STEP")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_seq")
    @SequenceGenerator(name = "step_seq", sequenceName = "STEP_SEQ", allocationSize = 1)
    @Column(name="step_id")
    private Long stepId;

    @ManyToOne
    @JoinColumn(name = "board_number", nullable = false)
    private Board board;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @Column(name = "step_description", length = 1000, nullable = false)
    private String stepDescription;

    @Column(name = "step_image", length = 255)
    private String stepImage;
}