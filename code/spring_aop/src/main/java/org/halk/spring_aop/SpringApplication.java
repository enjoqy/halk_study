package org.halk.spring_aop;

import org.halk.spring_aop.entity.SysUser;
import org.halk.spring_aop.service.SysUserService;
import org.halk.spring_aop.service.impl.SysUserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * aop使用哪种代理取决于目标对象是否有实现接口，以及项目配置（@EnableAspectJAutoProxy(proxyTargetClass = true)）
 * 关键代码在：
 *        public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
 * 		if (!IN_NATIVE_IMAGE &&
 * 				(config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config))) {
 * 			Class<?> targetClass = config.getTargetClass();
 *
 * @Author halk
 * @Date 2020/11/26 9:56
 */
@EnableAspectJAutoProxy//(proxyTargetClass = true)
@ComponentScan("org.halk.spring_aop.*")
public class SpringApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringApplication.class);
//        SysUserServiceImpl sysUserService = (SysUserServiceImpl) ac.getBean("sysUserService");
        SysUserService sysUserService = ac.getBean(SysUserService.class);
        SysUser sysUser = sysUserService.selectByPrimaryKey("0005932b-61c7-4c29-b29e-dd5df6baca37");
        System.out.println(sysUser);
    }
}
