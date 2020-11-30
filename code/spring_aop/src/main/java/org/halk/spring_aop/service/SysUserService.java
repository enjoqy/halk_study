package org.halk.spring_aop.service;

import org.halk.spring_aop.entity.SysUser;

/**
 * @Author halk
 * @Date 2020/11/25 16:43
 */
public interface SysUserService {

    SysUser selectByPrimaryKey(String id);
}
