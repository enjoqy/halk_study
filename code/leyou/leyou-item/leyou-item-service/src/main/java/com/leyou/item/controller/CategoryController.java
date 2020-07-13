package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.interfaces.PBEKey;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/9 10:48
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.Category>>
     * @Author halk
     * @Description 根据父节点的id查询子节点
     * @Date 2020/2/9 10:59
     * @Param [pid]
     **/
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        if (pid == null || pid < 0) {
            //400：参数错误
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)) {
            //404：资源未找到
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.notFound().build();
        }
        //200：ok
        return ResponseEntity.ok(categories);

    }

    /**
     * @Author halk
     * @Description 根据品牌id查询分类集合
     * @Date 2020/5/3 0003 10:21
     * @Param [bid]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryBrandById(@PathVariable("bid") Long bid){

        List<Category> categories = this.categoryService.queryCategoriesByBid(bid);

        if (CollectionUtils.isEmpty(categories)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(categories);
    }

    /**
     * @Author halk
     * @Description 根据id集合查询name
     * @Date 2020/5/13 0013 10:50
     * @Param [ids]
     * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
     **/
    @GetMapping
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(names);
    }




}
