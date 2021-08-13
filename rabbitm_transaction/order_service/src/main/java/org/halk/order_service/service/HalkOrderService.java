package org.halk.order_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.halk.order_service.entity.HalkOrder;

/**
 * 订单表(HalkOrder)表服务接口
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:18
 */
public interface HalkOrderService extends IService<HalkOrder> {

    /**
     * 创建一条订单
     *
     * @param orderId
     * @throws Exception
     */
    void createOrder(String orderId) throws Exception;
}
