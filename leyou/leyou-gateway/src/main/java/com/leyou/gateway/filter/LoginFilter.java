package com.leyou.gateway.filter;

import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.CookieUtils;
import com.leyou.common.utils.JwtUtils;
import com.leyou.gateway.config.FilterProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/6/2 0002 15:04
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    /**
     * 过滤前执行的方法
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤后执行的方法
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 是否执行过滤方法
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //获取白名单
        List<String> allowPaths = this.filterProperties.getAllowPaths();
        //初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String url = request.getRequestURL().toString();

        for (String allowPath : allowPaths) {
            if (StringUtils.contains(url, allowPath)){
                return false;
            }
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //初始化运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        //获取token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        try {
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        return null;
    }
}
