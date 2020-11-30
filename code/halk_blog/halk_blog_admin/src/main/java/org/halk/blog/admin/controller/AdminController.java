package org.halk.blog.admin.controller;

import org.halk.blog.admin.base.global.SysConf;
import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.service.AdminService;
import org.halk.blog.admin.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/29 10:53
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/queryAll")
    public String queryAll(){
        List<Admin> adminList = this.adminService.queryUserAll();
        return ResultUtil.result(SysConf.SUCCESS, adminList);
    }




}
