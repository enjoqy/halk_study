package org.halk.kill.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * item_kill
 * @author
 */
@Data
public class ItemKill implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 可被秒杀的总数
     */
    private Integer total;

    /**
     * 秒杀开始时间
     */
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    private Date endTime;

    /**
     * 是否有效（1=是；0=否）
     */
    private Byte isActive;

    /**
     * 创建的时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
