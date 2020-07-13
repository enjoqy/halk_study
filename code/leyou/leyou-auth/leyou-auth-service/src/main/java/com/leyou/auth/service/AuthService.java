package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JwtUtils;
import com.leyou.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author halk
 * @Date 2020/6/1 0001 17:11
 */
@Service
public class AuthService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserClient userClient;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthService.class);


    /**
     * @return java.lang.String
     * @Author halk
     * @Description 验证用户，并生成token
     * @Date 2020/6/1 0001 17:37
     * @Param [username, password]
     **/
    public String accredit(String username, String password) {

        User user = this.userClient.queryUser(username, password);

        //判断user
        if (user == null) {
            return null;
        }

        //生成token
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        try {
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            LOGGER.info("认证错误：{}" + e, userInfo);
            e.printStackTrace();
        }
        return null;
    }
}
