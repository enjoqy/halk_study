package org.halk.blog.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_tag
 * @author 
 */
@Data
public class Tag implements Serializable {
    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 标签内容
     */
    private String content;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 标签简介
     */
    private Integer clickCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;
}