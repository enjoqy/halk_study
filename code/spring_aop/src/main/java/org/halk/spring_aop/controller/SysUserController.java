package org.halk.spring_aop.controller;

import lombok.extern.slf4j.Slf4j;
import org.halk.spring_aop.entity.SysUser;
import org.halk.spring_aop.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author halk
 * @Date 2020/11/25 16:42
 */
@RestController
public class SysUserController {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

//    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login/{id}")
    public SysUser login(@PathVariable("id") String id){
        SysUser sysUser = this.sysUserService.selectByPrimaryKey(id);
        return sysUser;
    }
}
