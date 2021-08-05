package org.halk.spring_ioc;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自己实现@AutoWired注解
 * 通过反射为UserController注入UserService
 *
 * @Author halk
 * @Date 2020/12/3 16:51
 */
public class UserController {

    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
