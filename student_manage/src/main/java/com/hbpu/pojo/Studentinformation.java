package com.hbpu.pojo;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_studentinformation")
public class Studentinformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String wbh;

    @Column(name = "proNum")
    private String proNum;

    private String sname;

    private String ssex;

    private LocalDate sbirth;

    private String gkbmh;

    private String sidfy;

    private String sdept;

    private String byzy;

    private String getaward;

    private String advantage;

    private String haddress;

    private String sphone;

    private String lxaddress;

    private String postcode;

    private Integer proflag;

    @Column(name = "registerDate")
    private LocalDateTime registerDate;

    @Column(name = "BkYear")
    private String BkYear;

    private String spassword;

    private String snation;

    private String sps;

    private String zkzh;

    private String ksh;

    private String qq;

    private String semail;

    @Column( name = "giveUpOffer")
    private Integer giveUpOffer;

    @Column(name = "zKZflag")
    private Integer zKZflag;

    @Column(name = "questionOne")
    private Integer questionOne;

    @Column(name = "ansOne")
    private String ansOne;

    @Column(name = "questionTwo")
    private Integer questionTwo;

    @Column(name = "ansTwo")
    private String ansTwo;

    private Integer usertype;

}
