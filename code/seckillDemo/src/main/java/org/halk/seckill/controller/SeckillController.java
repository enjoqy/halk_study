package org.halk.seckill.controller;

import org.halk.seckill.entity.BaseResponse;
import org.halk.seckill.entity.Product;
import org.halk.seckill.service.OrderService;
import org.halk.seckill.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author halk
 * @Date 2020/8/6 0006 9:41
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private final static Logger log = LoggerFactory.getLogger(SeckillController.class);

    private static final String PREFIX = "seckill:";

    private static final ConcurrentHashMap<Integer, Boolean> productSoldOutMap = new ConcurrentHashMap();

    public static ConcurrentHashMap<Integer, Boolean> getProductSoldOutMap(){
        return productSoldOutMap;
    }

    @PostConstruct
    public void init() {
        //查询商品
        List<Product> products = this.productService.selectAll();
        products.forEach(x -> {
            redisTemplate.opsForValue().set(PREFIX + x.getId(), x.getStock().toString());
        });
    }

    @PostMapping("/{productId}")
    @ResponseBody
    public BaseResponse seckill(@PathVariable("productId") Integer productId) {

        if (productSoldOutMap.get(productId) != null) {
            return new BaseResponse(0, "商品已售完");
        }

        Long stock = this.redisTemplate.opsForValue().decrement(PREFIX + productId);
        log.info("===============>stock: " + stock);

        if (stock < 0) {
            //redis缓存一级优化
            stock = this.redisTemplate.opsForValue().increment(PREFIX + productId);
            log.info("===============>stock: " + stock);
            //jvm缓存二级优化
            productSoldOutMap.put(productId, true);
            return new BaseResponse(0, "商品已售完");
        }

        try {
            this.orderService.seckill(productId);
        } catch (Exception e) {
            stock = this.redisTemplate.opsForValue().increment(PREFIX + productId);
            log.info("===============>stock: " + stock);
            log.info("创建订单失败");
            if (productSoldOutMap.get(productId) != null) {
                productSoldOutMap.remove(productId);
            }
            return new BaseResponse(0, "创建订单失败");
        }
        return new BaseResponse(1, "创建订单成功");
    }
}
