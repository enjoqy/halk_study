package org.halk.blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.halk.blog.admin.base.mapper.SuperMapper;
import org.halk.blog.admin.entity.Blog;

import java.util.List;

/**
 * @Author halk
 * @Description
 * @Date 2020/9/15 10:51
 * @Param
 * @return
 **/
@Mapper
public interface BlogMapper extends SuperMapper<Blog> {

    /**
     * @Author halk
     * @Description  获取所有的博客文章
     * @Date 2020/9/15 13:56
     * @Param []
     * @return java.util.List<org.halk.blog.entity.Blog>
     **/
    @Select("select * from t_blog")
    List<Blog> getAll();
}
