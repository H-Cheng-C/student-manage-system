package com.hbpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hbpu.mapper")
public class StudentManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManageApplication.class, args);
    }

}
