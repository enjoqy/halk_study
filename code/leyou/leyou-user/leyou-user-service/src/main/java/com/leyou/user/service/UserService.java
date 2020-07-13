package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author halk
 * @Date 2020/5/29 0029 16:32
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 放入redis中数据加上前缀，后续方便区分
     */
    private final static String KEY_PREFIX = "user:verify:";

    /**
     * @return java.lang.Boolean
     * @Author halk
     * @Description 校验数据是否可用，要校验的数据类型：1，用户名；2，手机；
     * @Date 2020/5/29 0029 16:38
     * @Param [data, type]
     **/
    public Boolean checkUserData(String data, Integer type) {
        User record = new User();
        if (type == 1) {
            record.setUsername(data);
        } else if (type == 2) {
            record.setPhone(data);
        } else {
            return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }

    /**
     * @return void
     * @Author halk
     * @Description 发送短信验证码
     * @Date 2020/5/31 0031 16:47
     * @Param [phone]
     **/
    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)) {
            return;
        }

        //1，生成验证码
        String code = NumberUtils.generateCode(6);

        //2，发消息至rabbitMq
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        this.amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "VERIFY.CODE.SMS", msg);

        //3，把验证码保存至redis，过期时间5分钟
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    /**
     * @return void
     * @Author halk
     * @Description 用户注册功能
     * 1.对短信验证码校验，2.对密码使用md5加密，用随机码最为加密的盐
     * @Date 2020/5/31 0031 17:46
     * @Param [user, code]
     **/
    public void register(User user, String code) {

        //从redis中获取验证码，比较验证
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!StringUtils.equals(redisCode, code)) {
            return;
        }

        //2.生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //3.加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        //4.新增用户
        user.setId(null);
        user.setCreated(new Date());
        this.userMapper.insert(user);

        //5.删除redis中的验证码，节省内存
        this.redisTemplate.delete(KEY_PREFIX + user.getPhone());


    }

    /**
     * @return com.leyou.user.pojo.User
     * @Author halk
     * @Description 查询功能，根据参数中的用户名和密码查询指定用户
     * @Date 2020/6/1 0001 10:51
     * @Param [username, password]
     **/
    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);

        //判断user是否为空，
        if (user == null) {
            return null;
        }

        //加盐判断密码
        password = CodecUtils.md5Hex(password, user.getSalt());

        if (StringUtils.equals(password, user.getPassword())){
            return user;
        }

        return null;
    }
}
