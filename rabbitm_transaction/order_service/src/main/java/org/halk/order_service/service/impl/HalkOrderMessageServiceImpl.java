package org.halk.order_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.halk.order_service.entity.HalkOrder;
import org.halk.order_service.entity.HalkOrderMessage;
import org.halk.order_service.mapper.HalkOrderMapper;
import org.halk.order_service.mapper.HalkOrderMessageMapper;
import org.halk.order_service.service.HalkOrderMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单消息冗余表(HalkOrderMessage)表服务实现类
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:22
 */
@Service("halkOrderMessageService")
@RequiredArgsConstructor
public class HalkOrderMessageServiceImpl extends ServiceImpl<HalkOrderMessageMapper, HalkOrderMessage> implements HalkOrderMessageService {

    private final HalkOrderMessageMapper halkOrderMessageMapper;

}
