package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.Config;
import com.github.wxpay.sdk.WXPayRequest;
import com.github.wxpay.sdk.WXPayUtil;
import com.qingcheng.service.order.OrderService;
import com.qingcheng.service.order.WxPayService;
import com.qingcheng.util.HttpClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
@Service
public class WxPayServiceImpl implements WxPayService {

    @Autowired
     private Config config;

    public Map createNative(String orderId, Integer money, String notifyUrl,String... attach) throws Exception {
        //封装请求参数
        Map<String,String> map=new HashMap();
        map.put("appid",config.getAppID());//公众号ID
        map.put("mch_id",config.getMchID());//商户号
        map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        map.put("body","青橙");//商品描述
        map.put("out_trade_no",orderId);//订单号
        map.put("total_fee",money+"");//金额
        map.put("spbill_create_ip","127.0.0.1");//终端IP
        map.put("notify_url",notifyUrl);
        map.put("trade_type","NATIVE");//交易类型
        if (attach!=null&&attach.length>0){
            map.put("attach",attach[0]);
        }

        String xmlParam = WXPayUtil.generateSignedXml(map, config.getKey());//xml格式的参数
        System.out.println("参数："+xmlParam);

        //发送请求
        WXPayRequest wxPayRequest = new WXPayRequest(config);
//        String xmlResult = wxPayRequest.requestWithCert("/pay/unifiedorder", null, xmlParam, false);
        String xmlResult = wxPayRequest.requestWithoutCert("/pay/unifiedorder", null, xmlParam, false);
        System.out.println("结果："+xmlResult);


        //解析返回结果
        Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);

        Map m=new HashMap();
        m.put("code_url",mapResult.get("code_url"));
        m.put("total_fee",money+"");
        m.put("out_trade_no",orderId);

        return m;
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notifyLogic(String xml) throws Exception {
        //解析xml为map
        Map<String, String> map = WXPayUtil.xmlToMap(xml);

        //验证签名
        boolean signatureValid = WXPayUtil.isSignatureValid(map, config.getKey());
        System.out.println("验证签名是否正确:"+signatureValid);
        System.out.println(map.get("transaction_id"));
        System.out.println(map.get("out_trade_no"));
        System.out.println(map.get("result_code"));

        //修改订单状态
        if (signatureValid){
            if (map.get("result_code").equals("SUCCESS")){
                orderService.updatePayStatus(map.get("out_trade_no"),map.get("transaction_id"));
                //发送订单号给mq
                rabbitTemplate.convertAndSend("paynotify","",map.get("out_trade_no"));
            }else {
                //记录日志
            }

        }else {
            //记录日志
        }

    }

    public Map queryPayStatus(String orderId) {
        Map param = new HashMap();
        param.put("appid",config.getAppID());//公众号ID
        param.put("mch_id",config.getMchID());//商户号
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("out_trade_no",orderId);
        String url="https://api.mch.weixin.qq.com/pay/orderquery";
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, config.getKey());
            HttpClient client = new HttpClient(url);
            client.setHttps(true);
            client.setXmlParam(xmlParam);
            client.post();
            String result = client.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(result);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
