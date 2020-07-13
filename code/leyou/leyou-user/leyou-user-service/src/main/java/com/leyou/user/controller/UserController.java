package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author halk
 * @Date 2020/5/29 0029 16:33
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Boolean>
     * @Author halk
     * @Description 校验数据是否可用
     * @Date 2020/5/29 0029 16:36
     * @Param [data, type]
     **/
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,
                                                 @PathVariable("type") Integer type) {
        Boolean bool = this.userService.checkUserData(data, type);
        if (bool == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);
    }

    /**
     * @Author halk
     * @Description 发送短信验证码的接口
     * @Date 2020/5/31 0031 16:47
     * @Param [phone]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone")String phone){
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户注册功能
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated User user, @RequestParam("code")String code){
        this.userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Author halk
     * @Description 查询功能，根据参数中的用户名和密码查询指定用户
     * @Date 2020/6/1 0001 10:51
     * @Param [username, password]
     * @return org.springframework.http.ResponseEntity<com.leyou.user.pojo.User>
     **/
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username,
                                          @RequestParam("password")String password){
        User user = this.userService.queryUser(username, password);
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
}
