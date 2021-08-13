package org.halk.dispatcher_service.entity;
/**
 * 订单表(HalkOrder)实体类
 *
 * @author hd
 * @since 2021-08-13 10:12:19
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "halk_order")
public class HalkOrder implements Serializable {
    private static final long serialVersionUID = 744114463646451531L;
    /**
     * 订单id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单内容
     */
    private String orderContent;
    /**
     * 创建时间
     */
    private Date createTime;

}
