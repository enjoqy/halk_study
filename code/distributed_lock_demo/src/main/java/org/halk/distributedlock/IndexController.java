package org.halk.distributedlock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author halk
 * @Date 2020/8/11 0011 15:54
 */
@Controller
public class IndexController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;

    @GetMapping("/deduct_stock")
    @ResponseBody
    public String deductStock() {

//        String lockKey = "lockKey";
        String lockKey = UUID.randomUUID().toString();
//        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, "true");
//        redisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);

        /**
         * 可以保证加锁、设置过期时间是一个原子操作
         */
//        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, "true", 10, TimeUnit.SECONDS);

//        if (!flag) {
//            return "服务端繁忙，稍后再试！";
//        }

        RLock redissonLock = redisson.getLock(lockKey);

        try {
            redissonLock.lock();

            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功，剩余库存为： " + realStock);
            } else {
                System.out.println("扣减失败，剩余库存不足");
            }
        } finally {
            redissonLock.unlock();
//            if (lockKey.equals(redisTemplate.opsForValue().get(lockKey))){
//                redisTemplate.delete(lockKey);
//            }
        }

        return "end";
    }

}
