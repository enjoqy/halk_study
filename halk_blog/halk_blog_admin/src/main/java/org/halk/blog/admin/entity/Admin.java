package org.halk.blog.admin.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * t_admin
 * @author
 */
@Data
@TableName("t_admin")
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {

    /**
     * 唯一uid
     */
    private String uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
    private String passWord;

    /**
     * 性别(1:男2:女)
     */
    private String gender;

    /**
     * 个人头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱验证码
     */
    private String validCode;

    /**
     * 自我简介最多150字
     */
    private String summary;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

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

    /**
     * 昵称
     */
    private String nickName;

    /**
     * QQ号
     */
    private String qqNumber;

    /**
     * 微信号
     */
    private String weChat;

    /**
     * 职业
     */
    private String occupation;

    /**
     * github地址
     */
    private String github;

    /**
     * gitee地址
     */
    private String gitee;

    /**
     * 拥有的角色uid
     */
    private String roleUid;

    /**
     * 履历
     */
    private String personResume;

    private static final long serialVersionUID = 6831239413119927466L;

    public Admin(String uid, String userName) {
        this.uid = uid;
        this.userName = userName;
    }

}
