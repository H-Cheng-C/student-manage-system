package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.seckill.SeckillGoods;
import com.qingcheng.service.seckill.SeckillGoodsService;
import com.qingcheng.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seckill/goods")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    /**
     * 加载对应时区的秒杀商品
     */
    @GetMapping("/list")
    public List<SeckillGoods> list(String time){
        return seckillGoodsService.list(time);
    }


    /**
     * 加载时间菜单
     * @return
     */
    @GetMapping("/menus")
    public List<Date> loadMenu(){
        return DateUtil.getDateMenus();
    }


    /**
     * g根据商品id查询商品详情
     * @param time
     * @param id
     * @return
     */
    @GetMapping("/one")
    public SeckillGoods one(String time,Long id){
        SeckillGoods one = seckillGoodsService.one(time, id);
        return one;
    }

}
