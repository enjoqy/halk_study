package org.halk.spring_aop.dao;

import org.apache.ibatis.annotations.Mapper;
import org.halk.spring_aop.entity.SysUser;

@Mapper
public interface SysUserDao {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}
