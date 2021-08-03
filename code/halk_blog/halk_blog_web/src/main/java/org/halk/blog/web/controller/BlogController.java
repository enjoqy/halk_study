package org.halk.blog.web.controller;

import org.halk.blog.web.entity.Blog;
import org.halk.blog.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/15 11:02
 */
@Controller
@RequestMapping("/web")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/getAll")
    @ResponseBody
    public List<Blog> getAll(){
        return this.blogService.getAll();
    }


}
