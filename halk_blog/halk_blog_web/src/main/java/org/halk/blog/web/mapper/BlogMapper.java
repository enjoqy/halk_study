package org.halk.blog.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.halk.blog.web.entity.Blog;

import java.util.List;

/**
 * @Author halk
 * @Description
 * @Date 2020/9/15 10:51
 * @Param
 * @return
 **/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

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
