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

    @Column(name = "board_content", length = 1000, nullable = false)
    private String boardContent;

    @Column(name = "board_date", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime boardDate;

    @Column(name = "board_hit", columnDefinition = "NUMBER DEFAULT 0")
    private Integer boardHit;

    @Column(name = "board_like", columnDefinition = "NUMBER DEFAULT 0")
    private Integer boardLike;

    @OneToMany(mappedBy = "board")
    private List<Like> likes; //NullPointerException 뜨면 = new ArrayList<>(); 추가

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Step> steps;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}