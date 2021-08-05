package org.halk.spring_ioc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author halk
 * @Date 2020/12/3 16:54
 */
public class MyTest {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        UserController userController = new UserController();
        UserService userService = new UserService();
        System.out.println(userService);
        //第一种方式通过set注入
//        userController.setUserService(userService);
        //第二种方式通过反射注入
        Class<? extends UserController> userControllerClazz = userController.getClass();
        //获取属性
        Field userServiceField = userControllerClazz.getDeclaredField("userService");
        //私有属性需要修改权限才可以访问
        userServiceField.setAccessible(true);
        String name = userServiceField.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        String methodName = "set" + name;
        Method method = userControllerClazz.getMethod(methodName, UserService.class);
        //调用setUserService的方法
        method.invoke(userController, userService);
        System.out.println(userController);


    }
}
