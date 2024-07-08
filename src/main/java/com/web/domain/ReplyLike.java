package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_REPLY_LIKE", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_number", "reply_id"})})
public class ReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_like_seq")
    @SequenceGenerator(name = "reply_like_seq", sequenceName = "REPLY_LIKE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_number", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;
}