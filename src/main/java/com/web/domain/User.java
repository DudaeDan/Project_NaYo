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
@Table(name = "NY_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long userNumber;

    @Column(unique = true)
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
}
