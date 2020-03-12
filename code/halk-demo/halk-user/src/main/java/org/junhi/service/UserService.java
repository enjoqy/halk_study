package org.junhi.service;

import org.junhi.pojo.User;

import java.util.List;

/**
 * @author junhi
 * @date 2019/12/22 15:03
 */

public interface UserService {

    public User queryUserById(Long id);

    public void deleteUserById(Long id);

    public List<User> queryUserAll();
}
