package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Sku;
import com.qingcheng.pojo.order.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * sku业务逻辑层
 */
public interface SkuService {


    public List<Sku> findAll();


    public PageResult<Sku> findPage(int page, int size);


    public List<Sku> findList(Map<String, Object> searchMap);


    public PageResult<Sku> findPage(Map<String, Object> searchMap, int page, int size);


    public Sku findById(String id);

    public void add(Sku sku);


    public void update(Sku sku);


    public void delete(String id);

    public void saveAllPriceToRedis();

    /**
     * 根据skuId查询价格
     * @param id
     * @return
     */
    public Integer findPrice(String id);

    /**
     * 根据skuId更新商品价格
     * @param id
     * @param price
     */
    public void savePriceToRedisById(String id,Integer price);

    /**
     * 根据sku id删除商品价格缓存
     * @param id
     */
    public void deletePriceFromRedis(String id);

    /**
     * 批量扣除库存
     * @param orderItemList
     */
    public boolean deductionStock(List<OrderItem> orderItemList);

}
