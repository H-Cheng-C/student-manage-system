package com.hbpu.service;

import com.hbpu.mapper.StuSecurityMapper;
import com.hbpu.mapper.UserMapper;
import com.hbpu.pojo.StudentVo;
import com.hbpu.pojo.Studentinformation;
import com.hbpu.pojo.Stusecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StuSecurityMapper stuSecurityMapper;
//    public List<User> selectUse(String username){
//        User record=new User();
//        record.setUsername(username);
//        Example example = new Example(record.getClass());
//
//        List<User> users = userMapper.selectByExample(example);
//        return users;
//    }

//    public Studentinformation queryUserByPhone(String phone){
//        Studentinformation record=new Studentinformation();
//        record.setSphone(phone);
//        Studentinformation student = this.userMapper.selectOne(record);
//        return student;
//    }

    public int zhuceinsert(StudentVo studentVo){

        Random random = new Random();
        Studentinformation studentinformation=new Studentinformation();
        studentinformation.setSphone(studentVo.getSphone());
        studentinformation.setWbh(studentVo.getSidfy()+""+studentVo.getSphone());
        studentinformation.setSpassword(studentVo.getSpassword());
        studentinformation.setSname(studentVo.getSname());
        studentinformation.setSidfy(studentVo.getSidfy());
        studentinformation.setSemail(studentVo.getSemail());
        studentinformation.setQuestionOne(studentVo.getQuestionOne());
        studentinformation.setAnsOne(studentVo.getAnsOne());
        studentinformation.setQuestionTwo(studentVo.getQuestionTwo());
        studentinformation.setAnsTwo(studentVo.getAnsTwo());
        studentinformation.setUsertype(1);
        int i = this.userMapper.insert(studentinformation);
        return i;
    }

    public Studentinformation queryquesBysphone(String phone) {
        Studentinformation studentinformation=new Studentinformation();
        studentinformation.setSphone(phone);
        Studentinformation stuInformation = this.userMapper.selectOne(studentinformation);
        return stuInformation;
    }

    public String queryQues(int queNum){
        Stusecurity stusecurity = new Stusecurity();
        stusecurity.setSecNum(queNum);
        Stusecurity stusecurity1 =this.stuSecurityMapper.selectOne(stusecurity);

        return stusecurity1.getQue();
    }

    public List<String> queryAnsByPhone(String phone) {
        List<String> list=new ArrayList<>();
        Studentinformation student = new Studentinformation();
        student.setSphone(phone);
        Studentinformation studentinformation = this.userMapper.selectOne(student);
        list.add(studentinformation.getAnsOne());
        list.add(studentinformation.getAnsTwo());
        return list;
    }

    public int pwdReset(String phone) {
        Studentinformation student = new Studentinformation();
        student.setSphone(phone);
        Studentinformation studentinformation = this.userMapper.selectOne(student);
        String sidfy=studentinformation.getSidfy();
        String res=sidfy.substring(sidfy.length()-6);
        int count = this.userMapper.pwdupdate(res, phone);
        return count;
    }
}
