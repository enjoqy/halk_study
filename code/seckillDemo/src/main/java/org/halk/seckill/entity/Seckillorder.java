package org.halk.seckill.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * seckillorder
 * @author 
 */
@Data
public class Seckillorder implements Serializable {
    private Integer id;

    private Integer productid;

    /**
     * 秒杀数量
     */
    private BigDecimal amount;

    private static final long serialVersionUID = 1L;
}