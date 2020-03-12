package org.junhi.service.client;

import org.junhi.service.pojo.User;
import org.springframework.stereotype.Component;

/**
 * 熔断之后默认返回的返回值
 * @Author halk
 * @Date 2019/12/25 17:14
 */
@Component
public class UserClientFallback implements UserClient {
    @Override
    public User queryUserById(Long id) {
        return User.builder().username("服务器正忙，请稍后再试！").build();
    }
}
