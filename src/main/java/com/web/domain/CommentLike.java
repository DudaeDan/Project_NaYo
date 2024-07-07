package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "COMMENT_LIKE", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_number", "comment_id"})})
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_like_seq")
    @SequenceGenerator(name = "comment_like_seq", sequenceName = "COMMENT_LIKE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_number", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comments comment;

}