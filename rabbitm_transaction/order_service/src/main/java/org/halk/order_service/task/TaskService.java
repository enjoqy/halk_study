package org.halk.order_service.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halk.order_service.entity.HalkOrder;
import org.halk.order_service.entity.HalkOrderMessage;
import org.halk.order_service.mapper.HalkOrderMapper;
import org.halk.order_service.mapper.HalkOrderMessageMapper;
import org.halk.order_service.mq.OrderMQService;
import org.halk.order_service.util.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author zhuhao
 * @Date 2021/8/13 0013 14:47
 * @desc 当 rabbitmq 出现宕机之后，将数据库 冗余表中的 status=0的消息查询出来，重写投递到交换机中！
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final RabbitTemplate rabbitTemplate;
    private final HalkOrderMessageMapper orderMessageMapper;
    private final OrderMQService mqService;
    private final HalkOrderMapper orderMapper;

    /**
     * 每隔5s发一次！
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void sendMessage() {
        LambdaQueryWrapper<HalkOrderMessage> wrapper = Wrappers.<HalkOrderMessage>lambdaQuery()
                .eq(HalkOrderMessage::getStatus, 0);
        List<HalkOrderMessage> halkOrderMessages = this.orderMessageMapper.selectList(wrapper);
        for (HalkOrderMessage halkOrderMessage : halkOrderMessages) {
            HalkOrder halkOrder = this.orderMapper.selectById(halkOrderMessage.getOrderId());
            log.info("定时任务：再次投递消息  " + halkOrderMessage);

            this.mqService.sendMessage(halkOrder);
            //TODO 需要做一个标记，对同一个消息循环投递n次失败后
        }
    }

}
