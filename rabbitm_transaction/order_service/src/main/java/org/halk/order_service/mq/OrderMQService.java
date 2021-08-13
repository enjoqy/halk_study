package org.halk.order_service.mq;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halk.order_service.entity.HalkOrder;
import org.halk.order_service.entity.HalkOrderMessage;
import org.halk.order_service.mapper.HalkOrderMessageMapper;
import org.halk.order_service.service.HalkOrderService;
import org.halk.order_service.util.JsonUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author zhuhao
 * @Date 2021/8/13 0013 11:33
 * @desc
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMQService {

    private final HalkOrderMessageMapper orderMessageMapper;
    private final RabbitTemplate rabbitTemplate;

    /**
     * 消息投递成功回调函数
     */
    @PostConstruct
    public void regCallback() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息是否确认投递成功： " + ack);

                String orderId = correlationData.getId();

                if (!ack) {
                    log.info("消息投递失败原因： " + cause);
                    log.info("mq消息投递失败" + orderId);
                    return;
                }

                //消息投递成功修改数据库消息冗余表
                HalkOrderMessage halkOrderMessage = new HalkOrderMessage();
                halkOrderMessage.setStatus(1);
                LambdaQueryWrapper<HalkOrderMessage> wrapper = Wrappers.<HalkOrderMessage>lambdaQuery()
                        .eq(HalkOrderMessage::getOrderId, orderId)
                        .eq(HalkOrderMessage::getStatus, "0");
                int updateCount = orderMessageMapper.update(halkOrderMessage, wrapper);

                if (updateCount == 1) {
                    log.info("本地消息状态修改成功，消息成功投递到消息队列中...");
                } else {
                    log.info("本地消息状态投递失败，等待重新投递");
                }

            }
        });
    }

    /**
     * 投递消息
     *
     * @param order
     */
    public void sendMessage(HalkOrder order) {
        this.rabbitTemplate.convertAndSend(
                "order_topic_exchange",
                "order.queue",
                JsonUtils.toStringValue(order),
                new CorrelationData(order.getId())
        );
        log.info("消息开始投递： " + order);
    }

}
