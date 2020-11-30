package org.halk.blog.admin.service.impl;

import org.halk.blog.admin.base.serviceImpl.SuperServiceImpl;
import org.halk.blog.admin.entity.Blog;
import org.halk.blog.admin.mapper.BlogMapper;
import org.halk.blog.admin.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author halk
 * @Date 2020/9/15 11:02
 */
@Service
public class BlogServiceImpl extends SuperServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> getAll(){
        List<Blog> blogs = this.blogMapper.selectList(null);
        return blogs;
    }

    @Override
    public Boolean add(Blog blog) {

        String uid = UUID.randomUUID().toString().replace("-", "");
        blog.setUid(uid);
        blog.setStatus(true);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setSort(0);
        blog.setOpenComment(true);
        blog.setStartComment(true);

        return this.blogMapper.insert(blog) > 0;
    }
}
