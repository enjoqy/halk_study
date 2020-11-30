package org.halk.spring_aop.springaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.halk.spring_aop.controller.SysUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author halk
 * @Date 2020/11/25 17:20
 */
@Aspect
@Component
public class MyAspect {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    /**
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     * 这里问号表示当前项可以有也可以没有，其中各项的语义如下：
     * <p>
     * modifiers-pattern：方法的可见性，如public，protected；
     * ret-type-pattern：方法的返回值类型，如int，void等；
     * declaring-type-pattern：方法所在类的全路径名，如com.spring.Aspect；
     * name-pattern：方法名类型，如buisinessService()；
     * param-pattern：方法的参数类型，如java.lang.String；
     * throws-pattern：方法抛出的异常类型，如java.lang.Exception；
     */
    @Pointcut("execution(* org.halk.spring_aop.service.impl.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        Object proceed = null;
        try {
             proceed = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("time is : " + (end - start));

        return proceed;
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("-------before---");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("-------after---");
    }

}
