package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qingcheng.pojo.seckill.SeckillGoods;
import com.qingcheng.service.seckill.SeckillGoodsService;
import com.qingcheng.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据时间区间查询商品信息
     * @param time
     * @return
     */
    public List<SeckillGoods> list(String time) {
        String key="SeckillGoods_"+time;

        return redisTemplate.boundHashOps(key).values();
    }


    public SeckillGoods one(String time, Long id) {
         SeckillGoods goods= (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_"+time).get(id);
         return goods;
    }
}
