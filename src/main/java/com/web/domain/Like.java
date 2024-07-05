package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "LIKES")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @SequenceGenerator(name = "like_seq", sequenceName = "LIKE_SEQ", allocationSize = 1)
    @Column(name="like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_number", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_number", nullable = false)
    private Board board;
}
