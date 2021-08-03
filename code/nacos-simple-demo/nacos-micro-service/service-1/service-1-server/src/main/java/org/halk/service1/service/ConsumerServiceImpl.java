package org.halk.service1.service;

import org.halk.service1.api.ConsumerService;

/**
 * @Author halk
 * @Date 2021/2/1 10:46
 */
@org.apache.dubbo.config.annotation.Service  //注解标记此类的方法暴露为dubbo接口
public class ConsumerServiceImpl implements ConsumerService {


    @Override
    public String service() {
        return "ConsumerServiceImpl";
    }
}
