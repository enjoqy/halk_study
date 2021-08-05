package org.halk.seckill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.halk.seckill.entity.Seckillorder;

@Mapper
public interface SeckillOrderMapper{

    int deleteByPrimaryKey(Integer id);

    int insert(Seckillorder record);

    int insertSelective(Seckillorder record);

    Seckillorder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Seckillorder record);

    int updateByPrimaryKey(Seckillorder record);
}
