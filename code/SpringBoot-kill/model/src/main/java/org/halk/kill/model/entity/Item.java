package org.halk.kill.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * item
 * @author
 */
@Data
public class Item implements Serializable {
    private Integer id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     * 是否有效（1=是；0=否）
     */
    private Integer isActive;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
