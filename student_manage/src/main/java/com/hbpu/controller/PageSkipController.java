package com.hbpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageSkipController {

//    @RequestMapping("/main")
//    public String tomain(){
//        return "/tem/main";
//    }
//    @RequestMapping("/tologin")
//    public String tologin(){
//        return "login";
//    }
//    @RequestMapping("user/add")
//    public String add(){
//        return "tadd";
//    }
//
//    @RequestMapping("user/update")
//    public String update(){
//        return "tupdate";
//    }

    @RequestMapping({"/","login"})
    public String toIndex(Model model){
//        model.addAttribute("msg","hello shiro");
        return "login";
    }

    @RequestMapping("/zhuce")
    public String tozhuce(){
        return "zhuce";
    }

    @RequestMapping("/forgetpwd")
    public String toforget(){
        return "forgetpwd";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }

    @RequestMapping("/left")
    public String toleft(){
        return "left";
    }
    @RequestMapping("/desktop")
    public String todesktop(){
        return "desktop";
    }
    @RequestMapping("/default")
    public String todefault(){
        return "default";
    }
    @RequestMapping("/top")
    public String totop(){
        return "top";
    }

    @RequestMapping("/main")
    public String tomain(){
        return "main";
    }

    @RequestMapping("/adddba")
    public String toadddba(){
        return "adddba";
    }

    @RequestMapping("/admininfo")
    public String toadmininfo(){
        return "admininfo";
    }

    @RequestMapping("/dbapwdindentify")
    public String todbapwdindentify(){
        return "dbapwdindentify";
    }

    @RequestMapping("/toshouquanhuishou")
    public String toshouquanhuishou(){
        return "shouyuhuishou";
    }


}
