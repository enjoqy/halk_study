package org.halk.blog.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.halk.blog.admin.base.global.SysConf;
import org.halk.blog.admin.config.JwtProperties;
import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.entity.Role;
import org.halk.blog.admin.service.AdminService;
import org.halk.blog.admin.service.AuthService;
import org.halk.blog.admin.service.RoleService;
import org.halk.blog.admin.utils.JsonUtils;
import org.halk.blog.admin.utils.JwtUtils;
import org.halk.blog.admin.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author halk
 * @Date 2020/9/29 15:07
 */
@RestController
@RequestMapping("/auth")
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public String login(@RequestBody String json) {

        String username = JsonUtils.getValue(json, SysConf.USER_NAME);
        String password = JsonUtils.getValue(json, SysConf.PASS_WORD);

        //用户名和密码不能为空
        if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
            return ResultUtil.result(SysConf.ERROR, "用户名或密码不能为空");
        }

        Admin admin = this.authService.login(username, password);

        if (null == admin.getUid()) {
            return ResultUtil.result(SysConf.ERROR, "用户名或密码错误");
        }

        String token = "";

        try {
            token = JwtUtils.generateToken(admin, this.jwtProperties.getPrivateKey(), 30);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("生成token错误： {}" + e, admin);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("message", "登录成功");

        return ResultUtil.result(SysConf.SUCCESS, map);
    }

    /**
     * @Author halk
     * @Description 根据token中信息获取登录人的详细信息
     * @Date 2020/10/3 21:51
     * @Param [token]
     * @return java.lang.String
     **/
    @GetMapping("/info")
    public String info(HttpServletRequest request, @RequestParam("token") String token){

        String uid = request.getAttribute(SysConf.ADMIN_UID).toString();

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        Admin admin = this.adminService.getOne(wrapper);

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("uid", admin.getRoleUid());
        Role role = this.roleService.getOne(roleQueryWrapper);

        Map<String, Object> map = new HashMap<>();

        List<String> list = new ArrayList<>();
        list.add(role.getRoleName());
        map.put("roles", list);
        map.put("introduction", admin.getSummary());
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", admin.getUserName());

        return ResultUtil.result(SysConf.SUCCESS, map);
    }

}
