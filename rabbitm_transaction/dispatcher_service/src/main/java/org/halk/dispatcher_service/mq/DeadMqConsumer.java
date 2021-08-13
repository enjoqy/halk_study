package org.halk.dispatcher_service.mq;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halk.dispatcher_service.entity.HalkDispatcherOrder;
import org.halk.dispatcher_service.entity.HalkOrder;
import org.halk.dispatcher_service.mapper.HalkDispatcherOrderMapper;
import org.halk.dispatcher_service.util.JsonUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Author zhuhao
 * @Date 2021/8/13 0013 17:33
 * @desc
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeadMqConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final HalkDispatcherOrderMapper dispatcherOrderMapper;

    /**
     * 解决消息重试的集中方案：
     * 1: 控制重发的次数 + 死信队列
     * 2: try+catch+手动ack
     * 3: try+catch+手动ack + 死信队列处理 + 人工干预
     *
     * @param orderMsg
     * @param channel
     * @param correlationData
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = {"dead.order.queue"})
    public void messageConsumer(
            String orderMsg,
            Channel channel,
            CorrelationData correlationData,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {
        HalkOrder halkOrder = JsonUtils.parseStr(orderMsg, HalkOrder.class);
        //订单id
        String orderId = halkOrder.getId();
        log.info("死信队列收到mq消息： " + JsonUtils.toStringValue(halkOrder));

        //查询数据库中是否已经有订单了，保持幂等
        LambdaQueryWrapper<HalkDispatcherOrder> wrapper = Wrappers.<HalkDispatcherOrder>lambdaQuery()
                .eq(HalkDispatcherOrder::getOrderId, orderId);
        Integer count = this.dispatcherOrderMapper.selectCount(wrapper);

        try {
            if (count == 0) {
                HalkDispatcherOrder halkDispatcherOrder = new HalkDispatcherOrder();
                halkDispatcherOrder.setOrderId(orderId);
                halkDispatcherOrder.setCreateTime(new Date());
                halkDispatcherOrder.setOrderContent(halkOrder.getOrderContent());
                halkDispatcherOrder.setUserId(halkOrder.getUserId());
                halkDispatcherOrder.setStatus(0);
                this.dispatcherOrderMapper.insert(halkDispatcherOrder);
            }
            //手动ack确认消息
            channel.basicAck(tag, false);
        } catch (IOException e) {
            log.error("人工干预");
            log.error("发短信预警");
            log.error("同时把消息转移别的存储DB", e);

            channel.basicAck(tag, false);
        }

    }
}
