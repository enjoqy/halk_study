package org.halk.blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.halk.blog.admin.base.mapper.SuperMapper;
import org.halk.blog.admin.entity.Admin;

@Mapper
public interface AdminMapper extends SuperMapper<Admin> {
}
