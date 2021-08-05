package org.halk.seckill.service;

import org.halk.seckill.entity.Product;
import org.halk.seckill.entity.Seckillorder;
import org.halk.seckill.mapper.ProductMapper;
import org.halk.seckill.mapper.SeckillOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/8/6 0006 9:57
 */
@Service
public class OrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Transactional(rollbackFor=Exception.class)
    public void seckill(Integer productId){

        Product product = this.productMapper.selectByPrimaryKey(productId);

        if (product.getStock() <= 0){
            throw  new RuntimeException("商品已售完");
        }

        //创建秒杀订单
        Seckillorder order = new Seckillorder();
        order.setAmount(product.getPrice());
        order.setProductid(productId);

        this.seckillOrderMapper.insert(order);

        //减库存
        int updateNum = this.productService.deductProductStock(productId);
        if (updateNum <= 0){
            throw new RuntimeException("商品库存已售完");
        }
    }
}
