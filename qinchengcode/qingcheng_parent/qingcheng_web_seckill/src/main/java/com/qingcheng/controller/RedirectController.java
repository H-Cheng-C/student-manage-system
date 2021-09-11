package com.qingcheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redirect")
public class RedirectController {

    /**
     * 跳转方法
     * @param referer 用户访问该方法来源的页面地址
     * @return
     */
    @RequestMapping("/back")
    public String back(@RequestHeader(value = "referer",required = false) String referer){
        if (!StringUtils.isEmpty(referer)){
            return "redirect:"+referer;
        }
        return "/seckill-index.html";
    }

}
