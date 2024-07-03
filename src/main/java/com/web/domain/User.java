package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name="user_number")
    private Long userNumber;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userNickname;

    private String userAddress;

    @Column(nullable = false)
    private String userPhonenumber;

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    private Date userRegistdate;

    @Column(nullable = false)
    private Integer userRole; // 0: 일반, 1: 관리자

    // board의 작성자 표시용
    @OneToMany(mappedBy = "user")
    private List<Board> boards;
    // 한 유저가 한 게시글에 하나의 좋아요만 등록할 수 있음을 확인하기 위함
    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    public boolean checkPassword(String userPw){
        return this.userPw.equals(userPw);
    }
}
    