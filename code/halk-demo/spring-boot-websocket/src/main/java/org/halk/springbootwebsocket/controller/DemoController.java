package org.halk.springbootwebsocket.controller;

import org.halk.springbootwebsocket.util.WebSocketServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author halk
 * @Date 2020/7/13 0013 20:22
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }
}
