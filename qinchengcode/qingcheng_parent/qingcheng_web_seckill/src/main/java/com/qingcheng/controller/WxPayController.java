package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.wxpay.sdk.WXPayUtil;
import com.qingcheng.pojo.order.Order;
import com.qingcheng.pojo.seckill.SeckillOrder;
import com.qingcheng.service.order.OrderService;
import com.qingcheng.service.order.WxPayService;
import com.qingcheng.service.seckill.SeckillOrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class WxPayController {

    @Reference
    private SeckillOrderService seckillOrderService;

    @Reference
    private WxPayService wxPayService;

    @GetMapping("/createNative")
    public Map createNative(String orderId) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        SeckillOrder seckillOrder = seckillOrderService.QueryByUserName(userName);
        if (seckillOrder!=null){
            if (seckillOrder.getStatus().equals("0")&&seckillOrder.getStatus().equals("0")&&seckillOrder.getUserId().equals(userName)){
                return  wxPayService.createNative(seckillOrder.getId().toString(), (int)(seckillOrder.getMoney().floatValue()*100), "http://1b7mt1r.nat.ipyingshe.com/pay/notify.do",userName);
            }
        }else {
            return null;
        }
        return null;
    }

    @RequestMapping("/notify")
    public void notifylogic(HttpServletRequest request) throws Exception {
        System.out.println("支付成功，回调");
        InputStream inputStream =(InputStream) request.getInputStream();
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inputStream.close();
        String result=new String(outputStream.toByteArray(),"utf-8");
        System.out.println(result);

        Map<String, String> map = WXPayUtil.xmlToMap(result);

        //获取用户名
        String username = map.get("attach");

        seckillOrderService.updateStatus(map.get("out_trade_no"),username,map.get("transaction_id"));

    }

    @GetMapping("/queryPayStatus")
    public Map queryPayStatus(String orderId){
        return wxPayService.queryPayStatus(orderId);
    }

}
