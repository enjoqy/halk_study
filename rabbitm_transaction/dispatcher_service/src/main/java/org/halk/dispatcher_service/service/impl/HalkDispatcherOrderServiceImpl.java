package org.halk.dispatcher_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.halk.dispatcher_service.entity.HalkDispatcherOrder;
import org.halk.dispatcher_service.mapper.HalkDispatcherOrderMapper;
import org.halk.dispatcher_service.service.HalkDispatcherOrderService;
import org.springframework.stereotype.Service;

/**
 * 派单表(HalkDispatcherOrder)表服务实现类
 *
 * @author zhuhao
 * @since 2021-08-13 16:32:54
 */
@Service("halkDispatcherOrderService")
@RequiredArgsConstructor
public class HalkDispatcherOrderServiceImpl extends ServiceImpl<HalkDispatcherOrderMapper, HalkDispatcherOrder> implements HalkDispatcherOrderService {

    private final HalkDispatcherOrderMapper halkDispatcherOrderMapper;


}
