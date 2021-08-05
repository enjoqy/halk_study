package com.leyou.search.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @Author halk
 * @Date 2020/5/19 0019 11:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResult extends PageResult<Goods> {

    /**
     * 用来搜索的聚合分类结果集
     */
    private List<Map<String, Object>> categories;

    /**
     * 用来搜索的聚合品牌结果集
     */
    private List<Brand> brands;

    /**
     * 用来搜索的聚合规格参数结果集
     */
    private List<Map<String, Object>> specs;


    public SearchResult(List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(Long total, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
