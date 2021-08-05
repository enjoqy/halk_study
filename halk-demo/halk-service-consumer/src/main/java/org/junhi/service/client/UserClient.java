package org.junhi.service.client;

import org.junhi.service.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author halk
 * @Date 2019/12/25 16:58
 */
@FeignClient(value = "service-provider", fallback = UserClientFallback.class)
//@RequestMapping("user")  //这个注解目前不能使用，坑
public interface UserClient {

    /**
     * @Author halk
     * @Description 远程调用的接口
     * @Date 2019/12/25 17:01
     * @Param [id]
     * @return org.junhi.service.pojo.User
     **/
    @GetMapping("user/{id}")
    User queryUserById(@PathVariable("id") Long id);
}
