package org.halk.blog.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_picture
 * @author 
 */
@Data
public class TPicture implements Serializable {
    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 图片uid
     */
    private String fileUid;

    /**
     * 图片名
     */
    private String picName;

    /**
     * 分类uid
     */
    private String pictureSortUid;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}