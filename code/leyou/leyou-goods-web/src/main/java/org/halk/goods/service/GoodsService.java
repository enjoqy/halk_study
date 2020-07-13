package org.halk.goods.service;

import com.leyou.item.pojo.*;
import org.halk.goods.client.BrandClient;
import org.halk.goods.client.CategoryClient;
import org.halk.goods.client.GoodsClient;
import org.halk.goods.client.SpecificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author halk
 * @Date 2020/5/24 0024 11:04
 */
@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specificationClient;

    /**
     * @Author halk
     * @Description 详情页的数据加载组装
     * @Date 2020/5/24 0024 11:11
     * @Param [spuId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> loadData(Long spuId){
        Map<String, Object> model = new HashMap<>(10);

        //查询spu
        Spu spu = this.goodsClient.querySpuById(spuId);
        //查询spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spuId);
        //查询分类，Map<String, Object>
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }

        //查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //查询skus
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId);

        //查询规格参数组
        List<SpecGroup> groups = this.specificationClient.queryGroupWithParam(spu.getCid3());

        //查询特殊的规格参数组
        List<SpecParam> specParams = this.specificationClient.queryParams(null, spu.getCid3(), false, null);

        //初始化特殊规格参数的map
        Map<Long, Object> paramMap = new HashMap<>();
        specParams.forEach(specParam -> {
            paramMap.put(specParam.getId(), specParam.getName());
        });

        model.put("spu", spu);
        model.put("spuDetail", spuDetail);
        model.put("categories", categories);
        model.put("brand", brand);
        model.put("skus", skus);
        model.put("groups", groups);
        model.put("paramMap", paramMap);

        return model;
    }

}
