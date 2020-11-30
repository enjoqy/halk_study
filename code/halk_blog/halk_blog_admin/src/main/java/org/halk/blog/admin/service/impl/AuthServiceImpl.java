package org.halk.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.service.AdminService;
import org.halk.blog.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author halk
 * @Date 2020/9/29 15:08
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminService adminService;

    @Override
    public Admin login(String username, String password) {

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);

        Admin queryAmin = this.adminService.getOne(wrapper);

        if ( null == queryAmin){
            return new Admin();
        }
        //验证密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, queryAmin.getPassWord());

        return matches ? queryAmin : new Admin();
    }
}
