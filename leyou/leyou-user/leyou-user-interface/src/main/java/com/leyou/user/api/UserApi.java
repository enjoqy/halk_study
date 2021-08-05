package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 提供接口，通过feign进行远程调用
 * @Author halk
 * @Date 2020/6/1 0001 17:28
 */
public interface UserApi {

    /**
     * @Author halk
     * @Description 查询功能，根据参数中的用户名和密码查询指定用户
     * @Date 2020/6/1 0001 10:51
     * @Param [username, password]
     * @return org.springframework.http.ResponseEntity<com.leyou.user.pojo.User>
     **/
    @GetMapping("/query")
    User queryUser(@RequestParam("username")String username, @RequestParam("password")String password);
}
