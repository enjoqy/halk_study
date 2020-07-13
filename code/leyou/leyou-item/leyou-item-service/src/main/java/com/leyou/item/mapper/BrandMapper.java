package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/2/29 14:32
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * @Author halk
     * @Description 插入一条数据
     * @Date 2020/4/18 21:58
     * @Param [cid, bid]
     * @return void
     **/
    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{cid}, #{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * @Author halk
     * @Description 根据分类cid查询品牌集合
     * @Date 2020/4/22 17:37
     * @Param [cid]
     * @return java.util.List<com.leyou.item.pojo.Brand>
     **/
    @Select("select * from tb_brand a inner join tb_category_brand b on a.id=b.brand_id where b.category_id = #{cid}")
    List<Brand> selectBrandsByCid(Long cid);

    /**
     * @Author halk
     * @Description  根据品牌bid删除分类集合
     * @Date 2020/5/3 0003 10:45
     * @Param [bid]
     * @return void
     **/
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    void deleteBrandAndCategoryByBid(Long bid);

}
