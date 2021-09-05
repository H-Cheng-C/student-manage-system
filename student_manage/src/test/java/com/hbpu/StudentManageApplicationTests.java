package com.hbpu;

import com.hbpu.mapper.UserMapper;
import com.hbpu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootTest
class StudentManageApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    public int contextLoads() {
        int i = this.userMapper.pwdupdate("25671878888", "15671965631");
        return i;
    }

}
