package com.web.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "NY_NOTICE")
public class Notice {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NY_NOTICE_SEQ")
    @SequenceGenerator(name = "NY_NOTICE_SEQ", sequenceName = "NY_NOTICE_SEQ", allocationSize = 1)
    private Long noticeNumber;
	

    @Column( nullable = false)
    private String noticeTitle;

    @Column( nullable = false)
    private String noticeContent;
    

    @Column(insertable = false, updatable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    private Date noticeDate;

    @Column(name ="user_number", nullable = false)
    private Long userNumber;


    @ManyToOne
    @JoinColumn(name = "user_number", insertable = false, updatable = false)
    private User user;
	
}
