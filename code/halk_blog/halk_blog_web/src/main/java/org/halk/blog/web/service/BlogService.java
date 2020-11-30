package org.halk.blog.web.service;

import org.halk.blog.web.entity.Blog;
import org.halk.blog.web.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/15 11:02
 */
@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    public List<Blog> getAll(){
        List<Blog> blogs = this.blogMapper.selectList(null);
        return blogs;
    }




}
