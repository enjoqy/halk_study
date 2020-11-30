package org.halk.blog.admin.service;

import org.halk.blog.admin.entity.Admin;

/**
 * @Author halk
 * @Date 2020/9/29 15:08
 */
public interface AuthService {

    /**
     * @Author halk
     * @Description 登录
     * @Date 2020/9/29 15:15
     * @Param [admin]
     * @return java.lang.Boolean
     **/
    Admin login(String username, String password);
}
