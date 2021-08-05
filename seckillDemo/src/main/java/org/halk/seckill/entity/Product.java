package org.halk.seckill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * product
 * @author 
 */
@Data
public class Product implements Serializable {
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 图片
     */
    private String pic;

    private static final long serialVersionUID = 1L;
}