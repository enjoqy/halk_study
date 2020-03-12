package org.junhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junhi.mapper.UserMapper;
import org.junhi.pojo.User;
import org.junhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author junhi
 * @date 2019/12/22 15:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserById(Long id){
        return userMapper.selectById(id);
    }

    @Transactional
    public void deleteUserById(Long id){
        this.userMapper.deleteById(id);
    }

    public List<User> queryUserAll() {
        return this.userMapper.selectList(new QueryWrapper<User>());
    }
}
