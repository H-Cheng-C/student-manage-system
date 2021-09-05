package com.hbpu.controller;

import com.hbpu.pojo.StudentVo;
import com.hbpu.pojo.Studentinformation;
import com.hbpu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("sphone")String sphone, @RequestParam("spassword")String spassword, Model model,HttpServletResponse response
    ) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(sphone, spassword);
        try {
            subject.login(token);
            return "redirect:/default";
        } catch (UnknownAccountException e) {
//            model.addAttribute("msg","用户名错误");
            response.setContentType("text/html;charset=gb2312");
            PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('用户名错误！')</script>");
                //弹窗提示并跳转到其他页面
            return "login";
        } catch (IncorrectCredentialsException e){
//            model.addAttribute("msg","密码错误");
            response.setContentType("text/html;charset=gb2312");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('密码错误！')</script>");
            //弹窗提示并跳转到其他页面
            return "login";
        }
    }

    @RequiresAuthentication
    @RequestMapping("/logOut")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }
        return "login";
    }


    @PostMapping("/stuzhuce")
    public String stuzhuce(@RequestParam("sphone") String sphone,
                           @RequestParam("spassword") String spassword,
                           @RequestParam("qspassword") String qspassword,
                           @RequestParam("sname") String sname,
                           @RequestParam("sidfy") String sidfy,
                           @RequestParam("semail") String semail,
                           @RequestParam("questionOne") Integer questionOne,
                           @RequestParam("ansOne") String ansOne,
                           @RequestParam("questionTwo") Integer questionTwo,
                           @RequestParam("answerTwo") String answerTwo,
                           Model model){

        StudentVo studentVo=new StudentVo();
        studentVo.setSphone(sphone);studentVo.setSpassword(spassword);
        studentVo.setQspassword(qspassword);studentVo.setSname(sname);
        studentVo.setSidfy(sidfy);studentVo.setSemail(semail);
        studentVo.setQuestionOne(questionOne);studentVo.setAnsOne(ansOne);
        studentVo.setQuestionTwo(questionTwo);studentVo.setAnsTwo(answerTwo);
        System.out.println(questionOne);
        int i = 0;
        try {
            i = this.userService.zhuceinsert(studentVo);
            if(i==0){
                model.addAttribute("msg","注册失败");
                return "zhuce";
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return "login";
    }

    @PostMapping("/zhaohuipwd")
    public String zxhaohuipwd(@RequestParam("sphone")String phone, Model model){
        Studentinformation student = this.userService.queryquesBysphone(phone);
        if(student==null){
            model.addAttribute("msg","手机号输入错误");
            return "forgetpwd";
        }

        Integer q1 = student.getQuestionOne();
        String que1=this.userService.queryQues(q1);
        Integer q2=student.getQuestionTwo();
        String qu2=this.userService.queryQues(q2);
        model.addAttribute("que1",que1);
        model.addAttribute("que2",qu2);
        model.addAttribute("phone",phone);
        return "mibaoques";

    }

    @PostMapping("/checkans")
    public String checkans(@RequestParam("questionOne")String questionOne,
                           @RequestParam("questionTwo") String questionTwo,
                           @RequestParam("phone")String phone,
                           Model model,
                           HttpServletResponse response) throws IOException {
        List<String> ans = this.userService.queryAnsByPhone(phone);
        String ans1 = ans.get(0);
        String ans2 = ans.get(1);
        if((!ans1.equals(questionOne))||(!ans2.equals(questionTwo))){
            model.addAttribute("msg","答案错误！");
            model.addAttribute("phone",phone);
            return "mibaoques";
        }

        int res = this.userService.pwdReset(phone);
        if (res != 0) {
            response.setContentType("text/html;charset=gb2312");
            PrintWriter out = response.getWriter();
            if (res != 0) {
                out.print("<script language=\"javascript\">alert('密码已初始化为身份证后六位！')</script>");
                //弹窗提示并跳转到其他页面

            }
        }
        return "login";
    }
}
