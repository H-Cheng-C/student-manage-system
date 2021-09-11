package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.Result;
import com.qingcheng.service.seckill.SeckillOrderService;
import com.qingcheng.util.SeckillStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill/order")
public class SeckillOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 用户下单操作
     * @return
     */
    @GetMapping("/add")
    public Result add(Long id,String time){
        //获取用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals("anonymousUser")){
            return new Result(403,"未登录，请登陆!");
        }

        Boolean bo = seckillOrderService.add(id, time, username);
        if (bo){
            return new Result(0,"抢单成功!");
        }

        return new Result(1,"抢单失败!");
        //若没登录，则登陆

    }

    @RequestMapping("/query")
    public Result queryStatus(){
        //获取用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //用户未登陆
        if (name.equals("anonymousUser")){
            return new Result(403,"用户未登陆!");
        }
        try {
            SeckillStatus seckillStatus = seckillOrderService.queryStatus(name);
            if (seckillStatus!=null){
                Result result= new Result(seckillStatus.getStatus(),"抢单状态");
                result.setOther(seckillStatus);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(0,e.getMessage());
        }
        return new Result(404,"无相关信息");
    }

}
