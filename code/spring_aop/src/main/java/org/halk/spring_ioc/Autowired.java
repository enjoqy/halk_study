package org.halk.spring_ioc;

import java.lang.annotation.*;

/**
 * 自己实现spring的@AutoWired注解（自动注入功能）
 * @Author halk
 * @Date 2020/12/3 17:42
 */
@Target(ElementType.FIELD)  //作用范围，指明该类型的注解可以注解的程序元素的范围。该元注解的取值可以为TYPE,METHOD,CONSTRUCTOR,FIELD等。如果Target元注解没有出现，那么定义的注解可以应用于程序的任何元素。
@Retention(RetentionPolicy.RUNTIME)  //生效的范围，指明了该Annotation被保留的时间长短。RetentionPolicy取值为SOURCE,CLASS,RUNTIME。
@Documented  //指明拥有这个注解的元素可以被javadoc此类的工具文档化，非必须
@Inherited  //指明该注解类型被自动继承
@interface Autowired {
}
