package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/21 15:32
 */
public interface GoodsApi {

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.common.pojo.PageResult < com.leyou.item.bo.SpuBo>>
     * @Author halk
     * @Description 根据分页条件查询spu
     * @Date 2020/4/21 16:48
     * @Param [key, saleable, page, rows]
     **/
    @GetMapping("/spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) String saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 新增商品
     * @Date 2020/4/28 0028 17:29
     * @Param [spuBo]
     **/
    @PostMapping("/goods")
    Void saveGoods(@RequestBody SpuBo spuBo);

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 更新商品
     * @Date 2020/4/29 0029 11:52
     * @Param [spuBo]
     **/
    @PutMapping("/goods")
    Void updateGoods(@RequestBody SpuBo spuBo);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @Author halk
     * @Description 根据id查询spuDetail
     * @Date 2020/4/29 0029 11:04
     * @Param [spuId]
     **/
    @GetMapping("/spu/detail/{spuId}")
    SpuDetail querySpuDetailById(@PathVariable("spuId") Long spuId);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @Author halk
     * @Description 逻辑删除商品
     * @Date 2020/5/3 0003 20:15
     * @Param [spuId]
     **/
    @DeleteMapping("/spu/{spuId}")
    Void deleteSpuDetailById(@PathVariable("spuId") Long spuId);

    /**
     * @return org.springframework.http.ResponseEntity<java.util.List < com.leyou.item.pojo.Sku>>
     * @Author halk
     * @Description 根据spuId查询sku集合
     * @Date 2020/4/29 0029 11:11
     * @Param [spuId]
     **/
    @GetMapping("/sku/list")
    List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Spu>
     * @Author halk
     * @Description 根据id查询spu
     * @Date 2020/5/24 0024 10:21
     * @Param [id]
     **/
    @GetMapping("/{id}")
    Spu querySpuById(@PathVariable("id") Long id);

    /**
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Sku>
     * @Author halk
     * @Description 根据id查询sku
     * @Date 2020/6/8 0008 11:30
     * @Param [skuId]
     **/
    @GetMapping("/sku/{skuId}")
    Sku querySkuById(@PathVariable("skuId") Long skuId);
}
