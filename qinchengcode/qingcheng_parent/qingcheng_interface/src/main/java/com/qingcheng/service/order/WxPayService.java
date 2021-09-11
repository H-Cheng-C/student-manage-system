package com.qingcheng.service.order;

import java.util.Map;

public interface WxPayService {

    /**
     * 生成微信支付二维码
     * @param orderId
     * @param money
     * @param notifyUrl
     * @param attach 附加数据
     * @return
     */
    public Map createNative(String orderId,Integer money,String notifyUrl,String... attach) throws Exception;


    /**
     * 微信支付回调
     * @param xml
     */
    public void notifyLogic(String xml) throws Exception;

    /**
     * 查询支付状态
     * @param orderId
     * @return
     */
    public Map queryPayStatus(String orderId);
}
