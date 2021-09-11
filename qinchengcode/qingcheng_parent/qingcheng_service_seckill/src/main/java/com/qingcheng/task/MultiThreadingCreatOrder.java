package com.qingcheng.task;

import com.qingcheng.dao.SeckillGoodsMapper;
import com.qingcheng.pojo.seckill.SeckillGoods;
import com.qingcheng.pojo.seckill.SeckillOrder;
import com.qingcheng.util.IdWorker;
import com.qingcheng.util.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultiThreadingCreatOrder {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 异步执行的方法
     * 注解@Async
     */
    @Async
    public void createOrder(){
        try {
            System.out.println("--准备执行--");

            Thread.sleep(10000);

            SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();

            //用户抢单数据
            String username=seckillStatus.getUsername();
            String time=seckillStatus.getTime();
            Long id=seckillStatus.getGoodsId();

            //获取队列中的商品id
            Object sid = redisTemplate.boundListOps("SeckillGoodsCountList_" + id).rightPop();
            //卖完
            if (sid==null){
                //清理排队信息
                clearQueue(seckillStatus);
                return;
            }

            //查询商品详情
            SeckillGoods goods =(SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);

            if (goods!=null&&goods.getStockCount()>0){
                //创建订单
                SeckillOrder seckillOrder = new SeckillOrder();
                seckillOrder.setId(idWorker.nextId());
                seckillOrder.setSeckillId(id);
                seckillOrder.setMoney(goods.getCostPrice());
                seckillOrder.setUserId(username);
                seckillOrder.setSellerId(goods.getSellerId());
                seckillOrder.setCreateTime(new Date());
                seckillOrder.setStatus("0");
                redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

                //削减库存
                Long surplusCount = redisTemplate.boundHashOps("SeckillGoodsCount").increment(goods.getId(), -1);
                goods.setStockCount(surplusCount.intValue());
                //商品库存=0，将数据同步到mysql，并清理redis缓存
                if (surplusCount<=0){
                    seckillGoodsMapper.updateByPrimaryKey(goods);
                    //清理redis缓存
                    redisTemplate.boundHashOps("SeckillGoods_"+time).delete(id);
                }else {
                    //讲数据同步到redis
                    redisTemplate.boundHashOps("SeckillGoods_"+time).put(id,goods);
                }


                //变更抢单状态
                seckillStatus.setOrderId(seckillOrder.getId());
                seckillStatus.setMoney(seckillOrder.getMoney().floatValue());
                seckillStatus.setStatus(2);
                redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);


            }

            System.out.println("--正在执行--");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清理用户排队信息
     * @param seckillStatus
     */
    private void clearQueue(SeckillStatus seckillStatus) {
        //清理重复排队标识
        redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());
        //清理排队存储信息
        redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());
    }

}
