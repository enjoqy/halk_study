package org.halk.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.halk.order_service.entity.HalkOrderMessage;
import org.halk.order_service.service.HalkOrderMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单消息冗余表(HalkOrderMessage)表控制层
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:22
 */
@RestController
@RequestMapping("halkOrderMessage")
@RequiredArgsConstructor
public class HalkOrderMessageController {
    /**
     * 服务对象
     */
    private final HalkOrderMessageService halkOrderMessageService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public HalkOrderMessage selectOne(String id) {
        return this.halkOrderMessageService.getById(id);
    }

}
