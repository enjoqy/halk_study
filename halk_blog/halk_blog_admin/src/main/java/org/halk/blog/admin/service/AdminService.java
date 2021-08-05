package org.halk.blog.admin.service;

import org.halk.blog.admin.base.service.SuperService;
import org.halk.blog.admin.entity.Admin;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/29 11:16
 */
public interface AdminService extends SuperService<Admin> {

    /**
     * @Author halk
     * @Description 查询所有的用户
     * @Date 2020/9/29 14:36
     * @Param []
     * @return java.util.List<org.halk.blog.admin.entity.Admin>
     **/
    List<Admin> queryUserAll();


}
