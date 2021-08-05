package org.junhi.service.service.impl;

import org.junhi.service.mapper.UserMapper;
import org.junhi.service.pojo.User;
import org.junhi.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author halk
 * @Date 2019/12/23 14:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserById(Long id) {
        return this.userMapper.selectById(id);
    }
}
