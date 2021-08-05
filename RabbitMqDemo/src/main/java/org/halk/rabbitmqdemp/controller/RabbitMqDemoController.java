package org.halk.rabbitmqdemp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author halk
 * @Date 2020/7/24 0024 17:06
 */
@Controller
public class RabbitMqDemoController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("receive")
    public String receive(String message, Model model){
        String msg = "hello!  " + message;
        amqpTemplate.convertAndSend("spring.test.exchange", "a.b", msg);
        model.addAttribute("msg", msg);
        return "WeChat";
    }

}
