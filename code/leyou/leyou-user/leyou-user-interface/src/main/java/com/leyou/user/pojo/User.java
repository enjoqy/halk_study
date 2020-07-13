package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @Author halk
 * @Description 用户实体类
 * @Date 2020/5/29 0029 16:28
 * @Param
 * @return
 **/
@Data
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Length(min = 4, max = 30, message = "用户名的长度必须在4-30位之间")
    private String username;

    /**
     * 密码 ,@JsonIgnore该注解的意思是对象序列化为json字符串时，忽略该属性
     */
    @Length(min = 4, max = 30, message = "密码的长度必须在4-30位之间")
    @JsonIgnore
    private String password;

    /**
     * 电话
     */
    @Pattern(regexp = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$", message = "手机号不合法")
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码的盐值
     */
    @JsonIgnore
    private String salt;
}
