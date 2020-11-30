package org.halk.blog.admin.controller;

import org.halk.blog.admin.entity.Blog;
import org.halk.blog.admin.service.BlogService;
import org.halk.blog.admin.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/9/15 11:02
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<Blog>> getAll() {
        List<Blog> blogList = this.blogService.getAll();
        return ResponseEntity.ok(blogList);
    }

    /**
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author halk
     * @Description 增加一篇博客
     * @Date 2020/9/29 9:55
     * @Param [blog]
     **/
    @PostMapping("/add")
    public ResponseEntity<Void> addBlog(HttpServletRequest request, @RequestBody String blogJson) {

        Blog blog = JsonUtils.jsonToPojo(blogJson, Blog.class);
        Boolean flag = this.blogService.add(blog);

        if (flag) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }


}
