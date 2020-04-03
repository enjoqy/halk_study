package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author halk
 * @Date 2020/2/29 14:32
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
