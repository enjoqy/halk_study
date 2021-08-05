package com.leyou.item.service;

import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author halk
 * @Date 2020/2/9 10:45
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;


    /**
     * @Author halk
     * @Description 根据父节点的id查询子节点
     * @Date 2020/2/9 12:29
     * @Param [pid]
     * @return java.util.List<com.leyou.item.pojo.Category>
     **/
    public List<Category> queryCategoriesByPid(Long pid) {

        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    /**
     * @Author halk
     * @Description 根据id集合返回对应的分类的名称
     * @Date 2020/4/22 16:00
     * @Param [ids]
     * @return java.util.List<java.lang.String>
     **/
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }

    /**
     * @Author halk
     * @Description 根据品牌id查询分类集合
     * @Date 2020/5/3 0003 11:40
     * @Param [bid]
     * @return java.util.List<com.leyou.item.pojo.Category>
     **/
    public List<Category> queryCategoriesByBid(Long bid) {
        return this.categoryMapper.selectCateGoryByBid(bid);
    }
}
