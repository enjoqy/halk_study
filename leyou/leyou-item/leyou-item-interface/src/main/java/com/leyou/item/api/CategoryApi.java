package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/9 10:48
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.Category>>
     * @Author halk
     * @Description 根据父节点的id查询子节点
     * @Date 2020/2/9 10:59
     * @Param [pid]
     **/
    @GetMapping("list")
    List<Category> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @Author halk
     * @Description 根据品牌id查询分类集合
     * @Date 2020/5/3 0003 10:21
     * @Param [bid]
     **/
    @GetMapping("/bid/{bid}")
    List<Category> queryBrandById(@PathVariable("bid") Long bid);

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < java.lang.String>>
     * @Author halk
     * @Description 根据id集合查询name
     * @Date 2020/5/13 0013 10:50
     * @Param [ids]
     **/
    @GetMapping
    List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);


}
