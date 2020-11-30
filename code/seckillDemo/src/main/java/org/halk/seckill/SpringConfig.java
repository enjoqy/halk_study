package org.halk.seckill;

import org.apache.zookeeper.ZooKeeper;
import org.halk.seckill.zookeeper.ZooKeeperWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author halk
 * @Date 2020/8/6 0006 17:23
 */
@Configuration
public class SpringConfig {

    @Value("192.168.25.154:2181")
    private String zooKeeperAddress;

    @Autowired
    private ZooKeeperWatcher zooKeeperWatcher;

    @Bean
    public ZooKeeper initZooKeeper() throws IOException {
        return new ZooKeeper(zooKeeperAddress, 60000, zooKeeperWatcher);
    }
}
