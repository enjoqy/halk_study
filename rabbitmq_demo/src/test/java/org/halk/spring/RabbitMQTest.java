package org.halk.spring;

import org.halk.rabbitmq.spring.RabbitMQDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author zhuhao
 * @Date 2021/8/6 0006 17:19
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQDemoApplication.class)
public class RabbitMQTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        String msg = "hello, Spring boot amqp";

        for (int i = 0; i < 10; i++) {
            this.amqpTemplate.convertAndSend("spring.test.exchange","a.b", msg + i);
            System.out.println("send [X]  " + msg + i);

            Thread.sleep(100);
        }
        // 等待10秒后再结束
        Thread.sleep(10000);
    }
}
