package org.halk.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author zhuhao
 * @Date 2021/8/6 0006 16:51
 * @desc
 */
public class ConnectionUtil {

    /**
     * 建立与RabbitMQ的连接
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/halk");
        factory.setUsername("halk");
        factory.setPassword("halk");
        // 通过工程获取连接
        return factory.newConnection();
    }
}
