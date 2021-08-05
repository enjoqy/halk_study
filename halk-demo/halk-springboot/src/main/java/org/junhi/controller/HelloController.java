package org.junhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


/**
 * @author junhi
 * @date 2019/12/20 15:44
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("show")
    public String test(){
        return "hello controller!";
    }
}
