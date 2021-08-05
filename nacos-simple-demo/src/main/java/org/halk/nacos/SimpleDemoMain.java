package org.halk.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;

/**
 * 获取nacos中的配置
 *
 * @Author halk
 * @Date 2021/1/25 9:35
 */
public class SimpleDemoMain {


    public static void main(String[] args) throws NacosException {
        String serverAddr = "127.0.0.1:8848";
        String dataId = "nacos-simple-demo.yaml";
        String group = "DEFAULT_GROUP";

        ConfigService configService = NacosFactory.createConfigService(serverAddr);
        //String dataId, String group, long timeoutMs
        String config = configService.getConfig(dataId, group, 5000);
        System.out.println(config);
    }
}
