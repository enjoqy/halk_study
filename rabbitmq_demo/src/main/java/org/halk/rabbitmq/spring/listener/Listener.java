package org.halk.rabbitmq.spring.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author zhuhao
 * @Date 2021/8/6 0006 17:06
 * @desc
 */
@Component
public class Listener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"#.#"}
    ))
    public void listener(String msg) {
        System.out.println("listener 1  :" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue2", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"#.#"}
    ))
    public void listener2(String msg) {
        System.out.println("listener 2  :"  + msg);
    }
}
