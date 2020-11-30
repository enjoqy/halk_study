package org.halk.kill.model.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.halk.kill.model.entity.RandomCode;

public interface RandomCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RandomCode record);

    int insertSelective(RandomCode record);

    RandomCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RandomCode record);

    int updateByPrimaryKey(RandomCode record);
}
