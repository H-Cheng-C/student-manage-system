package com.qingcheng.timer;

import com.qingcheng.dao.SeckillGoodsMapper;
import com.qingcheng.pojo.seckill.SeckillGoods;
import com.qingcheng.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 秒杀商品定时导入到Redis
 */

@Component
@EnableScheduling
public class SeckillGoodsTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void LoadGoods(){
        System.out.println("==========执行定时任务===============");
        //查询所有的时间区间
        List<Date> dateMenus = DateUtil.getDateMenus();
        //循环时间区间，查询每个区间商品数据
        for (Date starTime : dateMenus) {
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();

            criteria.andEqualTo("status","1");
            criteria.andGreaterThan("stockCount","0");
            criteria.andGreaterThanOrEqualTo("startTime",starTime);
            criteria.andLessThanOrEqualTo("endTime",DateUtil.addDateHour(starTime,2));

            //过滤redis中存在的该区间的商品
            Set keys = redisTemplate.boundHashOps("SeckillGoods_" + DateUtil.date2Str(starTime)).keys();
            if (keys!=null && keys.size()>0){
                criteria.andNotIn("id",keys);
            }

            List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectByExample(example);

            //秒杀商品导入到Redis缓存
            for (SeckillGoods seckillGood : seckillGoods) {
                redisTemplate.boundHashOps("SeckillGoods_"+DateUtil.date2Str(starTime)).put(seckillGood.getId(),seckillGood);

                //剩余库存数，创建一个独立队列：存储商品剩余库存
                Long[] ids = push(seckillGood.getStockCount(), seckillGood.getId());
                //创建队列
                redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGood.getId()).leftPushAll(ids);
                //创建自定的key的值
                redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGood.getId(),seckillGood.getStockCount());
            }
        }

    }

    /**
     * 组装商品id，组装成数组
     * @param len
     * @param id
     * @return
     */
    public Long[] push(int len,Long id){
        Long[] ids = new Long[len];
        for (int i=0;i<len;i++){
            ids[i]=id;
        }
        return ids;
    }

}
