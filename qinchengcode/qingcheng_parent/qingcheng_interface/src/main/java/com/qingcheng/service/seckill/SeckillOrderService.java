package com.qingcheng.service.seckill;

import com.qingcheng.pojo.seckill.SeckillOrder;
import com.qingcheng.util.SeckillStatus;

public interface SeckillOrderService {


    public SeckillOrder QueryByUserName(String username);


    Boolean add(Long id,String time,String username);

    /**
     * 查询抢单状态
     */
    SeckillStatus queryStatus(String username);

    /**
     * 修改订单
     * @param outtradeno
     * @param username
     * @param transactionid
     */
    public void updateStatus(String outtradeno,String username,String transactionid);

}
