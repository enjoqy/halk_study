package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/29 14:35
 */
@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * @Author halk
     * @Description 根据查询条件分页并排序查询品牌信息
     * @Date 2020/2/29 15:04
     * @Param [key, page, rows, sortBy, desc]
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.PageResult<com.leyou.item.pojo.Brand>>
     **/
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc
    ){
        PageResult<Brand> result = this.brandService.queryBrandByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * @Author halk
     * @Description 新增品牌
     * @Date 2020/3/12 14:13
     * @Param [brand, cids]
     * @return org.springframework.http.ResponseEntity
     **/
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Author halk
     * @Description
     * @Date 2020/5/3 0003 10:28
     * @Param [brand, cids]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        this.brandService.updateBrand(brand, cids);
        return ResponseEntity.status((HttpStatus.CREATED)).build();
    }

    /**
     * @Author halk
     * @Description 根据分类cid查询品牌集合
     * @Date 2020/4/22 17:33
     * @Param [cid]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Brand>>
     **/
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsById(@PathVariable("cid") Long cid){
        List<Brand> brands = this.brandService.queryBrandsById(cid);
        if (CollectionUtils.isEmpty(brands)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * @Author halk
     * @Description 根据bid删除品牌和品牌与分类对应的关系
     * @Date 2020/5/3 0003 19:11
     * @Param [bid]
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     **/
    @DeleteMapping("/bid/{bid}")
    public ResponseEntity<Void> deleteBrandByBid(@PathVariable("bid")Long bid){
        this.brandService.deleteBrandByBid(bid);
        return ResponseEntity.ok().build();
    }

    /**
     * @Author halk
     * @Description 根据id查询品牌
     * @Date 2020/5/13 0013 10:52
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     **/
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand = this.brandService.queryBrandById(id);
        if (brand == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }
}
