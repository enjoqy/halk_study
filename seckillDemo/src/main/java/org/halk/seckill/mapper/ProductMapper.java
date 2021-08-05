package org.halk.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.halk.seckill.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper{

    @Update("update product set stock = stock - 1 where id = #{id} and stock > 0")
    int deductProductStock(@Param("id") Integer productId);

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    @Select("select * from product")
    List<Product> selectAll();

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}
