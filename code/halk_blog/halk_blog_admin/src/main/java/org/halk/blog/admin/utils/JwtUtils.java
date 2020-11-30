package org.halk.blog.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.halk.blog.admin.entity.Admin;
import org.joda.time.DateTime;


import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * jwt工具类
 *
 * @Author halk
 * @Description
 * @Date 2020/6/1 0001 16:29
 * @Param
 * @return
 **/
public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param admin         载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(Admin admin, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(AbstractJwtConstans.JWT_KEY_ID, admin.getUid())
                .claim(AbstractJwtConstans.JWT_KEY_USER_NAME, admin.getUserName())
                //时间处理的工具类
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param admin      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(Admin admin, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(AbstractJwtConstans.JWT_KEY_ID, admin.getUid())
                .claim(AbstractJwtConstans.JWT_KEY_USER_NAME, admin.getUserName())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static Admin getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new Admin(
               ObjectUtils.toString(body.get(AbstractJwtConstans.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(AbstractJwtConstans.JWT_KEY_USER_NAME))
        );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static Admin getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new Admin(
                ObjectUtils.toString(body.get(AbstractJwtConstans.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(AbstractJwtConstans.JWT_KEY_USER_NAME))
        );
    }
}
