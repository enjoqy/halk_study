package org.halk.dispatcher_service.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.halk.dispatcher_service.entity.HalkDispatcherOrder;
import org.halk.dispatcher_service.service.HalkDispatcherOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 派单表(HalkDispatcherOrder)表控制层
 *
 * @author zhuhao
 * @since 2021-08-13 16:32:54
 */
@RestController
@RequestMapping("/halkDispatcherOrder")
@RequiredArgsConstructor
@Api(value = "派单模块", tags = "派单模块")
public class HalkDispatcherOrderController {
    /**
     * 服务对象
     */
    private final HalkDispatcherOrderService halkDispatcherOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public HalkDispatcherOrder selectOne(String id) {
        return this.halkDispatcherOrderService.getById(id);
    }

}
