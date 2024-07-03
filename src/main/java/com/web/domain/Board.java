package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    @SequenceGenerator(name = "board_seq", sequenceName = "BOARD_SEQ", allocationSize = 1)
    @Column(name="board_number")
    private Long boardNumber;

    @ManyToOne
    @JoinColumn(name = "user_number")
    private User user;

    @Column(name = "main_img", length = 255)
    private String mainImg;

    @Column(name = "board_title", nullable = false, length = 255)
    private String boardTitle;

    @Column(name = "board_ingredient", nullable = false, length = 255)
    private String boardIngredient;

    @Column(name = "board_content_1", length = 255)
    private String boardContent1;

    @Column(name = "board_content_2", length = 255)
    private String boardContent2;

    @Column(name = "board_content_3", length = 255)
    private String boardContent3;

    @Column(name = "board_content_4", length = 255)
    private String boardContent4;

    @Column(name = "board_content_5", length = 255)
    private String boardContent5;

    @Column(name = "board_content_6", length = 255)
    private String boardContent6;

    @Column(name = "board_content_7", length = 255)
    private String boardContent7;

    @Column(name = "board_content_8", length = 255)
    private String boardContent8;

    @Column(name = "board_content_9", length = 255)
    private String boardContent9;

    @Column(name = "board_content_10", length = 255)
    private String boardContent10;

    @Column(name = "board_img_1", length = 255)
    private String boardImg1;

    @Column(name = "board_img_2", length = 255)
    private String boardImg2;

    @Column(name = "board_img_3", length = 255)
    private String boardImg3;

    @Column(name = "board_img_4", length = 255)
    private String boardImg4;

    @Column(name = "board_img_5", length = 255)
    private String boardImg5;

    @Column(name = "board_img_6", length = 255)
    private String boardImg6;

    @Column(name = "board_img_7", length = 255)
    private String boardImg7;

    @Column(name = "board_img_8", length = 255)
    private String boardImg8;

    @Column(name = "board_img_9", length = 255)
    private String boardImg9;

    @Column(name = "board_img_10", length = 255)
    private String boardImg10;

    @Column(name = "board_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime boardDate;

    @Column(name = "board_hit", columnDefinition = "NUMBER DEFAULT 0")
    private Integer boardHit;

    @Column(name = "board_like", columnDefinition = "NUMBER DEFAULT 0")
    private Integer boardLike;

    @OneToMany(mappedBy = "board")
    private List<Like> likes;
}
