package org.halk.spring_aop.service.impl;

import org.halk.spring_aop.dao.SysUserDao;
import org.halk.spring_aop.entity.SysUser;
import org.halk.spring_aop.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author halk
 * @Date 2020/11/26 9:59
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

//    @Autowired
//    private SysUserDao sysUserDao;

    @Override
    public SysUser selectByPrimaryKey(String id){
//        return this.sysUserDao.selectByPrimaryKey(id);

        SysUser sysUser = new SysUser();
        sysUser.setUsername("xiaohong");

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return sysUser;
    }
}
