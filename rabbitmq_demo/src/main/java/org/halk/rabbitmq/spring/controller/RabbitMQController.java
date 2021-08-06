package org.halk.rabbitmq.spring.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhuhao
 * @Date 2021/8/6 0006 18:08
 * @desc
 */
@RestController
public class RabbitMQController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/hello/{msg}")
    public String hello(@PathVariable String  msg){
        this.amqpTemplate.convertAndSend("spring.test.exchange", "a.b", msg);
        return "send message: " + msg;
    }
}
