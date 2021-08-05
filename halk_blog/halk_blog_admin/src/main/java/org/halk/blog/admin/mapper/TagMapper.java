package org.halk.blog.admin.mapper;

import org.halk.blog.admin.entity.Tag;

public interface TagMapper {
    int deleteByPrimaryKey(String uid);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}