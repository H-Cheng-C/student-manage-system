package com.hbpu.pojo;


import java.io.Serializable;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_admininformation")
public class Admininformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "userName")
    private Integer userName;

    private String tname;

    private String tpassword;


    private String tphone;

    private String temail;

    private Integer usertype;


}
