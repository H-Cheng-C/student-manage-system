package com.hbpu.pojo;


import java.io.Serializable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name = "tb_stusecurity")
public class Stusecurity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "secNum")
    private Integer secNum;

    private String que;


}
