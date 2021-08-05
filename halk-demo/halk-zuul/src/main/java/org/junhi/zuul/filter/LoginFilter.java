package org.junhi.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author halk
 * @Date 2019/12/26 16:23
 */
@Component
public class LoginFilter extends ZuulFilter {
    
    /**
     * @Author halk
     * @Description 过滤类型：pre、route、post、error
     * @Date 2019/12/26 16:26
     * @Param []
     * @return java.lang.String
     **/
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * @Author halk
     * @Description 执行顺序，返回值越小，优先级越高
     * @Date 2019/12/26 16:27
     * @Param []
     * @return int
     **/
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * @Author halk
     * @Description 是否要执行run方法
     * @Date 2019/12/26 16:28
     * @Param []
     * @return boolean
     **/
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @Author halk
     * @Description 执行过滤器的方法，业务逻辑
     * @Date 2019/12/26 16:29
     * @Param []
     * @return java.lang.Object
     **/
    @Override
    public Object run() throws ZuulException {
        //初始化context上下文对象， servlet spring
        RequestContext context = RequestContext.getCurrentContext();

        //获取request对象
        HttpServletRequest request = context.getRequest();

        //获取参数
        String token = request.getParameter("token");
        if(StringUtils.isBlank(token)){
            //拦截，不转发
            context.setSendZuulResponse(false);
            //相应状态码，401-身份为认证
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            //设置相应的提示
            context.setResponseBody("request error!");
        }

        return null;
    }
}
