package com.hbpu.pojo;

import java.time.LocalDateTime;

import java.io.Serializable;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_meun")
public class Meun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "meunID")
    private Integer meunID;

    @Column(name = "authorityName")
    private String authorityName;

    @Column(name = "userType")
    private Integer userType;

    @Column(name = "authoritylLink")
    private String authoritylLink;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "orderNo")
    private Integer orderNo;

    @Column(name = "timeFlag")
    private Integer timeFlag;

    @Column(name = "begDate")
    private LocalDateTime begDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;


}
