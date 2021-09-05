package com.hbpu.pojo;


import java.io.Serializable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_shouquan")
public class Shouquan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "meunID")
    private Integer meunID;

    @Column(name = "userName")
    private Integer userName;


}
