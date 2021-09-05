package com.hbpu.controller;

import com.hbpu.pojo.Admininformation;
import com.hbpu.pojo.Shouquan;
import com.hbpu.service.AdminService;
import com.hbpu.service.ShouQuanService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Request;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    
    @Autowired
    AdminService adminService;

    @Autowired
    ShouQuanService shouQuanService;

    @PostMapping("/pwdidentify")
    public String pwdidentify(@RequestParam("pwd")String pwd, Model model){
        Subject subject = SecurityUtils.getSubject();
        Admininformation currentUser =(Admininformation) subject.getPrincipal();
        Admininformation admin = this.adminService.queryAdminByPhone(currentUser.getTphone());
        if(!admin.getTpassword().equals(pwd)){
            model.addAttribute("msg","密码输入错误");
            return "dbapwdindentify";
        }

        return "adddba";
    }

    @PostMapping("/adddba")
    public String addaba(@RequestParam("tname")String tname,
                         @RequestParam("tpassword")String tpassword,
                         @RequestParam("tphone") String tphone,
                         @RequestParam("temail")String temail,
                         HttpServletResponse response) throws IOException {
        int i = this.adminService.insertAdmin(tname, tpassword, tphone, temail);
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
            if (i!=0) {
                out.print("<script language=\"javascript\">alert('添加成功！')</script>");
                //弹窗提示并跳转到其他页面
                return "redirect:/adminquery";
            }else {
                out.print("<script language=\"javascript\">alert('添加失败！')</script>");
                //弹窗提示并跳转到其他页面
                return "adddba";
            }
    }

    @GetMapping("/adminquery")
    public String queryAllAdmin(Model model){
        List<Admininformation> adminList = this.adminService.queryAllAdmin();
        model.addAttribute("list",adminList);
        return "admininfo";
    }

    @GetMapping("/shouyuhuishou")
    public String toshouyuhuishou(HttpServletRequest request, Model model){
        String currentname = request.getParameter("currentname");
        Admininformation admininformation = this.adminService.queryUserBycurrentame(currentname);
        Integer adminId = admininformation.getUserName();

        List<Shouquan> quanxianNumList = this.shouQuanService.selectshouquanTable(adminId);
        Map menuMap = this.adminService.queryMapByNumList(quanxianNumList);
        model.addAttribute("menuMap",menuMap);
        //没有的权限
        List<Integer> hasNotList = this.shouQuanService.queryHasNotmeunNum(quanxianNumList);
        Map<Integer, String> hasNouMeun = this.shouQuanService.queryMapHasNot(hasNotList);
        model.addAttribute("hasNouMenu",hasNouMeun);
        model.addAttribute("currentname",currentname);
        return "shouyuhuishou";
    }

    @PostMapping("/meungive")
    @ResponseBody
    public void meungive(String meunid,String name){
        Admininformation admininformation = this.adminService.queryUserBycurrentame(name);
        Integer adminId = admininformation.getUserName();
        int meId=Integer.valueOf(meunid);
        Integer count = this.adminService.insertMeunByMeunId(adminId, meId);

    }

    @PostMapping("/meunrevoke")
    @ResponseBody
    public void meunrevoke(String meunid,String name){
        Admininformation admininformation = this.adminService.queryUserBycurrentame(name);
        Integer adminId = admininformation.getUserName();
        int meid=Integer.valueOf(meunid);
        Integer count = this.adminService.deleteMeunByAdminId(adminId, meid);
    }

    @PostMapping("/deletedba")
    @ResponseBody
    public void deletedba(String dbaid){
        Integer id=Integer.valueOf(dbaid);
        this.adminService.deleteDbaById(id);
    }

    @PostMapping("/querylike")
    public String queryListByName(@RequestParam("tname") String tname,Model model){
        if(StringUtils.isEmpty(tname)==true){
            return "admininfo";
        }
        List<Admininformation> adminlist = this.adminService.queryLikeByName(tname);
        model.addAttribute("list",adminlist);
        return "querybyname";
    }
}
