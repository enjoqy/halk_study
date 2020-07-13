package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/21 15:32
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.PageResult < com.leyou.item.bo.SpuBo>>
     * @Author halk
     * @Description 根据分页条件查询spu
     * @Date 2020/4/21 16:48
     * @Param [key, saleable, page, rows]
     **/
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) String saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SpuBo> result = this.goodsService.querySpuByPage(key, saleable, page, rows);
        if (result == null || CollectionUtils.isEmpty(result.getItems())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 新增商品
     * @Date 2020/4/28 0028 17:29
     * @Param [spuBo]
     **/
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 更新商品
     * @Date 2020/4/29 0029 11:52
     * @Param [spuBo]
     **/
    @PutMapping("/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @Author halk
     * @Description 根据id查询spuDetail
     * @Date 2020/4/29 0029 11:04
     * @Param [spuId]
     **/
    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySpuDetailById(spuId);
        if (spuDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @Author halk
     * @Description 逻辑删除商品
     * @Date 2020/5/3 0003 20:15
     * @Param [spuId]
     **/
    @DeleteMapping("/spu/{spuId}")
    public ResponseEntity<Void> deleteSpuDetailById(@PathVariable("spuId") Long spuId) {
        this.goodsService.deleteSpuDetailById(spuId);
        return ResponseEntity.ok().build();
    }

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.Sku>>
     * @Author halk
     * @Description 根据spuId查询sku集合
     * @Date 2020/4/29 0029 11:11
     * @Param [spuId]
     **/
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Spu>
     * @Author halk
     * @Description 根据id查询spu
     * @Date 2020/5/24 0024 10:21
     * @Param [id]
     **/
    @GetMapping("/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id) {
        Spu spu = this.goodsService.querySpuById(id);
        if (spu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spu);
    }

    /**
     * @Author halk
     * @Description 根据id查询sku
     * @Date 2020/6/8 0008 11:30
     * @Param [skuId]
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Sku>
     **/
    @GetMapping("/sku/{skuId}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("skuId") Long skuId) {
        Sku sku = this.goodsService.querySkuById(skuId);
        if (sku == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sku);
    }

}
