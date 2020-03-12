package org.junhi.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.junhi.service.client.UserClient;
import org.junhi.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author halk
 * @Date 2019/12/23 15:16
 */
@Controller
@RequestMapping("consumer/user")
//@DefaultProperties(defaultFallback = "fallback")  // 全局的熔断方法
public class UserController {

    @Autowired
    private UserClient userClient;

   /* @Autowired
    private RestTemplate restTemplate;*/

//    @Autowired
//    private DiscoveryClient discoveryClient;

    /**
     * @Author halk
     * @Description 查询用户
     * @Date 2019/12/25 14:42
     * @Param [id]
     * @return java.lang.String
     **/
    @GetMapping
    @ResponseBody
    @HystrixCommand   // 加上熔断注解，熔断之后调用默认方法
    public String queryUserById(@RequestParam("id") Long id) throws JsonProcessingException {
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
//        ServiceInstance instance = instances.get(0);
//        return this.restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id, User.class);
        // 通过服务名称进行访问
       /* if(id == 28){
            throw new RuntimeException();
        }
        User user = this.restTemplate.getForObject("http://service-provider/user/" + id, User.class);
        return new ObjectMapper().writeValueAsString(user);*/

        User user = this.userClient.queryUserById(id);
        return new ObjectMapper().writeValueAsString(user);
    }

    /**
     * @Author halk
     * @Description 熔断之后默认调用的方法 ，全局调用的熔断方法
     * @Date 2019/12/25 14:41
     * @Param
     * @return java.lang.String
     **/
   /* public String fallback(){
        return "服务器正忙，请稍后再试";
    }*/
}
