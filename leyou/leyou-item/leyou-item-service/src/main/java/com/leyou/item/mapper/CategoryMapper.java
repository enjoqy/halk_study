package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/9 10:36
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {

    /**
     * @Author halk
     * @Description 根据bid查询分类集合
     * @Date 2020/5/3 0003 11:20
     * @Param [bid]
     * @return java.util.List<com.leyou.item.pojo.Category>
     **/
    @Select("select a.* from tb_category a left join tb_category_brand b on a.id = b.category_id where b.brand_id = #{bid}")
    List<Category> selectCateGoryByBid(Long bid);
}
