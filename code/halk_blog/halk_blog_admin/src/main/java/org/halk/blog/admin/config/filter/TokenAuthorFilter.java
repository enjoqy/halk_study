package org.halk.blog.admin.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.halk.blog.admin.base.global.SysConf;
import org.halk.blog.admin.config.JwtProperties;
import org.halk.blog.admin.entity.Admin;
import org.halk.blog.admin.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * token验证拦截
 *
 * @Author halk
 * @Date 2020/10/5 9:22
 */
@Component
@Slf4j
public class TokenAuthorFilter extends OncePerRequestFilter {

    @Value(value = "${tokenHeader}")
    private String tokenHeader;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }

    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //得到请求头信息authorization信息
        String token = request.getHeader(this.tokenHeader);

        if (StringUtils.isBlank(token)) {
            log.error("传递过来的token为: {}", token);
            return;
        }

        try {
            Admin admin = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            if (null != admin) {
                request.setAttribute(SysConf.ADMIN_UID, admin.getUid());
            }else {
                return;
            }
        } catch (Exception e) {
            log.error("传递过来的token为: {}" + token, e);
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);

    }
}
