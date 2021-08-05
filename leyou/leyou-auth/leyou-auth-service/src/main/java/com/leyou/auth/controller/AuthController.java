package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import com.netflix.client.http.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author halk
 * @Date 2020/6/1 0001 17:10
 */
@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description token认证
     * @Date 2020/6/1 0001 17:24
     * @Param [username, password, request, response]
     **/
    @PostMapping("/accredit")
    public ResponseEntity<Void> accredit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = this.authService.accredit(username, password);

        //token为空，返回未认证
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire());

        return ResponseEntity.ok().build();
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.UserInfo>
     * @Author halk
     * @Description 根据token验证用户信息
     * @Date 2020/6/2 0002 11:45
     * @Param [token]
     **/
    @GetMapping("/verify")
    public ResponseEntity<UserInfo> verify(HttpServletRequest request, HttpServletResponse response, @CookieValue("LY_TOKEN") String token) {
        UserInfo userInfo = null;
        try {
            //从token中获取用户信息
            userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if (userInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            //刷新jwt的有效时间
            token = JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());

            //刷新cookie中的有效时间
            CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getExpire() * 60);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("根据token验证用户信息错误： {}" + e, userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
