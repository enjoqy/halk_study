package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/29 14:35
 */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.PageResult < com.leyou.item.pojo.Brand>>
     * @Author halk
     * @Description 根据查询条件分页并排序查询品牌信息
     * @Date 2020/2/29 15:04
     * @Param [key, page, rows, sortBy, desc]
     **/
    @GetMapping("page")
    PageResult<Brand> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    );

    /**
     * @return org.springframework.http.ResponseEntity
     * @Author halk
     * @Description 新增品牌
     * @Date 2020/3/12 14:13
     * @Param [brand, cids]
     **/
    @PostMapping
    Void saveBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description
     * @Date 2020/5/3 0003 10:28
     * @Param [brand, cids]
     **/
    @PutMapping
    Void updateBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.Brand>>
     * @Author halk
     * @Description 根据分类cid查询品牌集合
     * @Date 2020/4/22 17:33
     * @Param [cid]
     **/
    @GetMapping("/cid/{cid}")
    List<Brand> queryBrandsById(@PathVariable("cid") Long cid);

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 根据bid删除品牌和品牌与分类对应的关系
     * @Date 2020/5/3 0003 19:11
     * @Param [bid]
     **/
    @DeleteMapping("/bid/{bid}")
    Void deleteBrandByBid(@PathVariable("bid") Long bid);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @Author halk
     * @Description 根据id查询品牌
     * @Date 2020/5/13 0013 10:52
     * @Param [id]
     **/
    @GetMapping("{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
}
