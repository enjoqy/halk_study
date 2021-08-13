package org.halk.order_service.entity;
/**
 * (HalkUser)实体类
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
@TableName(value = "halk_user")
public class HalkUser implements Serializable {
    private static final long serialVersionUID = 441859403967126905L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 名称
     */
    private String name;

}
