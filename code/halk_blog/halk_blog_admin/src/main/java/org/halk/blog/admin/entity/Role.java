package org.halk.blog.admin.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * t_role
 * @author
 */
@Data
@TableName("t_role")
public class Role implements Serializable {
    /**
     * 角色id
     */
    private String uid;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 角色介绍
     */
    private String summary;

    /**
     * 角色管辖的菜单的UID
     */
    private String categoryMenuUids;

    private static final long serialVersionUID = 1L;
}
