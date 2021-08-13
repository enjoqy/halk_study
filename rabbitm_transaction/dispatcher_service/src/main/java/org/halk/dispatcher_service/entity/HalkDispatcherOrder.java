package org.halk.dispatcher_service.entity;
/**
 * 派单表(HalkDispatcherOrder)实体类
 *
 * @author hd
 * @since 2021-08-13 16:32:54
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "halk_dispatcher_order")
public class HalkDispatcherOrder implements Serializable {
    private static final long serialVersionUID = -94727311951384139L;
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
     * 状态【0：消费失败，1：消费成功】
     */
    private Integer status;
    /**
     * 订单内容
     */
    private String orderContent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户id
     */
    private String userId;

}
