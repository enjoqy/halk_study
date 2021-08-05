package org.junhi.service.pojo;

/**
 * @author junhi
 * @date 2019/12/21 17:00
 */

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码加密的salt值
     */
    private String salt;


}
