package com.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "NY_LIKE")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_SEQ")
    @SequenceGenerator(name = "LIKE_SEQ", sequenceName = "LIKE_SEQ", allocationSize = 1)
    private Long likePk;

    @ManyToOne
    @JoinColumn(name = "boardNumber", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userNumber", nullable = false)
    private User user;
}
