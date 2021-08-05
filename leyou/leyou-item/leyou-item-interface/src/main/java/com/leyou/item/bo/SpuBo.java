package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/4/21 16:35
 */
@Data
public class SpuBo extends Spu {

    /**
     * 分类名称
     */
    private String cname;

    /**
     * 品牌名称
     */
    private String bname;

    /**
     * spu商品集的详细信息
     */
    private SpuDetail spuDetail;

    /**
     * 具体的sku商品集合
     */
    private List<Sku> skus;
}
