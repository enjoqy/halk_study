package org.halk.blog.admin.service;

import org.halk.blog.admin.base.service.SuperService;
import org.halk.blog.admin.entity.Blog;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/29 11:16
 */
public interface BlogService extends SuperService<Blog> {

    /**
     * @return java.util.List<org.halk.blog.admin.entity.Blog>
     * @Author halk
     * @Description 获取所有的blog
     * @Date 2020/9/29 11:17
     * @Param []
     **/
    List<Blog> getAll();

    /**
     * @return java.lang.Boolean
     * @Author halk
     * @Description 增加一篇博客
     * @Date 2020/9/29 11:18
     * @Param [blog]
     **/
    Boolean add(Blog blog);
}
