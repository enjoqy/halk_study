package org.halk.order_service.entity;
/**
 * 订单消息冗余表(HalkOrderMessage)实体类
 *
 * @author hd
 * @since 2021-08-13 10:12:22
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "halk_order_message")
public class HalkOrderMessage implements Serializable {
    private static final long serialVersionUID = 148541884017763338L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单内容
     */
    private String orderContent;
    /**
     * 消息发送状态【0：投递未成功，1：投递成功】
     */
    private Integer status;

}
