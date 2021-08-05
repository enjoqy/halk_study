package org.junhi.controller;

import org.junhi.pojo.User;
import org.junhi.service.UserService;
import org.junhi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author junhi
 * @date 2019/12/21 15:13
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author halk
     * @Description 查询 
     * @Date 2019/12/22 18:09
     * @Param [id]
     * @return org.junhi.pojo.User
     **/
    @GetMapping("{id}")
    @ResponseBody
    public User queryUserById(@PathVariable("id") Long id){
        return this.userService.queryUserById(id);
    }

    /**
     * @Author halk
     * @Description
     * @Date 2019/12/22 20:45
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("test")
    @ResponseBody
    public String test(){
        return "hello user";
    }

    @GetMapping("all")
    public String queryUserAll(Model model){
        List<User> users = this.userService.queryUserAll();
        model.addAttribute("users", users);
        return "users";
    }


}
