package org.halk.blog.admin.demo;

import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.utils.JwtUtils;
import org.halk.blog.admin.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "D:\\projects\\halk_study\\code\\halk_blog\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\projects\\halk_study\\code\\halk_blog\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new Admin("20L", "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6IjIwTCIsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTYwMTcwNzYzOH0.OfCX92rs-YDDL52X04NiT7yY1f4gDrkjedG44TfCPgCB7nfO8qYrskbFq_Kp58SDPt6U3zWPO_RQW_msttgAMq0kQN_9HVQO210Fk1tJgw-P9YyFwjT0dnag8a9G7Hv4kZ3ME936XhSgxwK_PrS9vcCY06T7XJLvgLn0bxu43Sg";

        // 解析token
        Admin admin = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + admin.getUid());
        System.out.println("userName: " + admin.getUserName());
    }
}
