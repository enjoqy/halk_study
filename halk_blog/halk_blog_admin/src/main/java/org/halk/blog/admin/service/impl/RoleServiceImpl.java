package org.halk.blog.admin.service.impl;

import org.halk.blog.admin.base.serviceImpl.SuperServiceImpl;
import org.halk.blog.admin.entity.Role;
import org.halk.blog.admin.mapper.RoleMapper;
import org.halk.blog.admin.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author halk
 * @Date 2020/10/4 9:20
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {
}
