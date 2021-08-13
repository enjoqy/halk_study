package org.halk.order_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author zhuhao
 * @Date 2021/8/13 0013 10:59
 * @desc
 */
@Configuration
public class RabbitMQConfiguration {

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public TopicExchange deadOrderExchange() {
        return new TopicExchange("dead_order_topic_exchange", true, false);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue deadOrderQueue() {
        return new Queue("dead.order.queue", true);
    }

    /**
     * 死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingDeadOrder() {
        return BindingBuilder.bind(deadOrderQueue()).to(deadOrderExchange()).with("dead.order.queue");
    }

    /**
     * 订单交换机
     *
     * @return
     */
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order_topic_exchange", true, false);
    }

    /**
     * 订单队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        //关联死信队列，过期的消息或者有异常的消息将进入死信队列
        HashMap<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", "dead_order_topic_exchange");
        return new Queue("order.queue", true, false, false, args);
    }

    /**
     * 订单队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingOrder() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.queue");
    }
}
