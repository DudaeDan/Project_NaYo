package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    @SequenceGenerator(name = "board_seq", sequenceName = "BOARD_SEQ", allocationSize = 1)
    private Long boardNumber;

    @Column(nullable = false)
    private String boardTitle;

    private String boardContent;

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    private Date boardDate;

    @Column(insertable = false, updatable = false, columnDefinition = "NUMBER DEFAULT 0")
    private Long boardHit;

    @Column(name ="user_number", nullable = false)
    private Long userNumber;

    private Long fileId;
    
    @Column
    private String thumbnailUrl; // 썸네일 URL
    
    @ManyToOne
    @JoinColumn(name = "user_number", insertable = false, updatable = false)
    private User user;
}
