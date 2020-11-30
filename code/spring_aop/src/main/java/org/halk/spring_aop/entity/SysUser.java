package org.halk.spring_aop.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements Serializable {
    /**
     * uuid主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名或者昵称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * salt，用于密码加密
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 0:初始化，1:激活, 2:禁用
     */
    private Integer status;

    /**
     * 1:超级管理员 2 机构管理员 3 校级管理员 4 教师 5学生 6家长
     */
    private Integer userType;

    /**
     * 根据user_type对应机构，学校，教师，学生;家长对应学生为学生ID
     */
    private String relativeId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 如果用户类型为机构管理员，则为机构编号，如果用户类型为学校管理员，则为学校对应机构编号，如果用户类型为教师，则为教师所属学校对应机构ID，如为学生，表示学生所在学校对应机构ID。
     */
    private String relativeOrganizationId;

    /**
     * 如果用户类型为学校管理员，则为学校编号，如果用户类型是教师，则为教师所属学校编号，如果是学生，则为学生所属学校编号
     */
    private String relativeSchoolId;

    /**
     * 用户头像地址
     */
    private String avatar;

    private static final long serialVersionUID = 1L;
}