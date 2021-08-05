package org.halk.seckill;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Author halk
 * @Date 2020/8/9 0009 18:14
 */
public class Demo {

    public static void main(String[] args) {
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8002));
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8003));
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8004));
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8005));
        jedisClusterNode.add(new HostAndPort("192.168.25.154", 8006));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 10, config);

        System.out.println(jedisCluster.set("student", "xuehuafei"));
        System.out.println(jedisCluster.set("student2", "mantian"));

        System.out.println(jedisCluster.get("student"));
        System.out.println(jedisCluster.get("student2"));

        jedisCluster.close();
    }
}
