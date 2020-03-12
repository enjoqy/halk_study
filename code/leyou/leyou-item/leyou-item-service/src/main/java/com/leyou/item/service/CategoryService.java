package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/9 10:45
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


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
}
