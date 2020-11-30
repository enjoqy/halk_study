package org.halk.blog.admin.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @Author halk
 * @Date 2020/9/29 9:56
 */
@SpringBootTest
public class Demo01 {

    @Test
    public void Test01(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);


    }
}
