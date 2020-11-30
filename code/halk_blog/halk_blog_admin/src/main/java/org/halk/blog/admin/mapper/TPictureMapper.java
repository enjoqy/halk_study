package org.halk.blog.admin.mapper;

import org.halk.blog.admin.entity.TPicture;

public interface TPictureMapper {
    int deleteByPrimaryKey(String uid);

    int insert(TPicture record);

    int insertSelective(TPicture record);

    TPicture selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(TPicture record);

    int updateByPrimaryKey(TPicture record);
}