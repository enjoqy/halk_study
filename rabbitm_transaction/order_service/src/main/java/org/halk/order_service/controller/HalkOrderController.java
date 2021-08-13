package org.halk.order_service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.halk.order_service.entity.HalkOrder;
import org.halk.order_service.service.HalkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 订单表(HalkOrder)表控制层
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:19
 */
@RestController
@RequestMapping("/halkOrder")
@RequiredArgsConstructor
@Api(value = "订单模块", tags = "订单模块")
public class HalkOrderController {
    /**
     * 服务对象
     */
    private final HalkOrderService halkOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过主键查询单条数据")
    @GetMapping("selectOne")
    public HalkOrder selectOne(String id) {
        return this.halkOrderService.getById(id);
    }


    /**
     * 创建一条订单
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "创建一条订单")
    @ApiImplicitParam(name = "orderId", value = "orderId")
    @PostMapping("createOrder")
    public ResponseEntity<Void> createOrder(@RequestParam(value = "orderId") String orderId) throws Exception {
        this.halkOrderService.createOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
