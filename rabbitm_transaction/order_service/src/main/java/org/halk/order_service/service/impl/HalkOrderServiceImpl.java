package org.halk.order_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.halk.order_service.entity.HalkOrder;
import org.halk.order_service.entity.HalkOrderMessage;
import org.halk.order_service.mapper.HalkOrderMapper;
import org.halk.order_service.mapper.HalkOrderMessageMapper;
import org.halk.order_service.mq.OrderMQService;
import org.halk.order_service.service.HalkOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单表(HalkOrder)表服务实现类
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:18
 */
@Service("halkOrderService")
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class HalkOrderServiceImpl extends ServiceImpl<HalkOrderMapper, HalkOrder> implements HalkOrderService {

    private final HalkOrderMapper orderMapper;
    private final OrderMQService orderMQService;
    private final HalkOrderMessageMapper orderMessageMapper;

    @Override
    public void createOrder(String orderId) throws Exception {
        //插入订单到数据库
        HalkOrder halkOrder = new HalkOrder();
        if (StringUtils.isNotBlank(orderId)){
            halkOrder.setId(orderId);
        }
        halkOrder.setOrderContent("来一桶泡面");
        halkOrder.setCreateTime(new Date());
        halkOrder.setUserId("1001");
        int insert = this.orderMapper.insert(halkOrder);
        log.info("创建订单：" + halkOrder);
        if (insert != 1){
            throw new Exception("订单创建失败，原因[数据库操作失败]");
        }

        //插入到消息冗余表
        HalkOrderMessage halkOrderMessage = new HalkOrderMessage();
        halkOrderMessage.setOrderId(halkOrder.getId());
        halkOrderMessage.setOrderContent(halkOrder.getOrderContent());
        halkOrderMessage.setStatus(0);
        this.orderMessageMapper.insert(halkOrderMessage);
        log.info("创建消息冗余： " + halkOrderMessage);

        //投递消息
        this.orderMQService.sendMessage(halkOrder);
    }
}
