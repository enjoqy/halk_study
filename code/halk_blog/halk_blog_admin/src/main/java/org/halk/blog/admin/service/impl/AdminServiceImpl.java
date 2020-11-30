package org.halk.blog.admin.service.impl;

import org.halk.blog.admin.base.serviceImpl.SuperServiceImpl;
import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.mapper.AdminMapper;
import org.halk.blog.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/29 11:01
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> queryUserAll() {
        List<Admin> admins = this.adminMapper.selectList(null);
        return admins;
    }
}
