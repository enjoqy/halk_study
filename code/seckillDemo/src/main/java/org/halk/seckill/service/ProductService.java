package org.halk.seckill.service;

import org.halk.seckill.entity.Product;
import org.halk.seckill.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/8/6 0006 9:42
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * @Author halk
     * @Description 减库存
     * @Date 2020/8/6 0006 10:21
     * @Param [productId]
     * @return int
     **/
    public int deductProductStock(Integer productId) {
        return this.productMapper.deductProductStock(productId);
    }

    public List<Product> selectAll() {
        return this.productMapper.selectAll();
    }
}
