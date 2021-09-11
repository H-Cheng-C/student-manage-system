package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.service.goods.SkuSearchService;
import com.qingcheng.service.goods.SkuService;
import com.qingcheng.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class SearchController {

    @Reference
    private SkuSearchService skuSearchService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam Map<String, String> searchMap) throws Exception {
        //字符集处理
         searchMap = WebUtil.convertCharsetToUTF8(searchMap);

         //没有页码默认为1
         if (searchMap.get("pageNo")==null){
             searchMap.put("pageNo","1");
         }

         //页面传两个参数给后端，sort：排序字段，sortOrder：排序规则
        if (searchMap.get("sort")==null){
            searchMap.put("sort","");
        }
        if (searchMap.get("sortOrder")==null){
            searchMap.put("sortOrder","DESC");
        }

        Map result = skuSearchService.search(searchMap);
        model.addAttribute("result",result);

        //url处理
        StringBuffer url=new StringBuffer("/search.do?");
        for(String key:searchMap.keySet()){
            url.append("&"+key+"="+searchMap.get(key));
        }
        model.addAttribute("url",url);

        model.addAttribute("searchMap",searchMap);

        int pageNo=Integer.parseInt(searchMap.get("pageNo"));
        model.addAttribute("pageNo",pageNo);

        Long totalPages=(Long) result.get("totalPages");//得到总页数

        int startPage=1;//开始页码
        int endPage=totalPages.intValue();//截止页码

        if (totalPages>5){
            startPage=pageNo-2;
            if (startPage<1){
                startPage=1;
            }
            endPage=startPage+4;
        }

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "search";
    }
}
