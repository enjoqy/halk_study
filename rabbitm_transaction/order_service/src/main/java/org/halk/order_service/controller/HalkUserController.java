package org.halk.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.halk.order_service.entity.HalkUser;
import org.halk.order_service.service.HalkUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (HalkUser)表控制层
 *
 * @author zhuhao
 * @since 2021-08-13 10:12:22
 */
@RestController
@RequestMapping("halkUser")
@RequiredArgsConstructor
public class HalkUserController {
    /**
     * 服务对象
     */
    private HalkUserService halkUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public HalkUser selectOne(String id) {
        return this.halkUserService.getById(id);
    }

}
