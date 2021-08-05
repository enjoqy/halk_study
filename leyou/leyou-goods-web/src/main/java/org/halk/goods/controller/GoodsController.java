package org.halk.goods.controller;

import org.halk.goods.service.GoodsHtmlService;
import org.halk.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Author halk
 * @Date 2020/5/22 0022 15:00
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * @Author halk
     * @Description 使用thymeleaf模板返回商品详情页，
     * @Date 2020/5/24 0024 10:20
     * @Param [spuId, model]
     * @return java.lang.String
     **/
    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id")Long spuId, Model model){
        Map<String, Object> map = this.goodsService.loadData(spuId);
        model.addAllAttributes(map);

        //生成静态文件
        this.goodsHtmlService.asyncExecute(spuId);

        return "item";
    }
}
