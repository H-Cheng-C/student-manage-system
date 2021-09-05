package com.hbpu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo {
    private String wbh;

    private String sphone;

    private String spassword;

    private String qspassword;

    private String sname;

    private String sidfy;

    private String semail;

    private Integer questionOne;

    private String ansOne;

    private Integer questionTwo;

    private String ansTwo;
}
