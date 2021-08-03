package org.halk.spring_ioc;

import java.util.stream.Stream;

/**
 * @Author halk
 * @Date 2020/12/3 17:38
 */
public class MyTest2 {

    public static void main(String[] args) {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();

        //同等效果
//        Field[] declaredFields = clazz.getDeclaredFields();
//        Arrays.asList(declaredFields).forEach();

        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            //获取属性是否有注解
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation != null){
                field.setAccessible(true);
                //获取当前属性的类型
                Class<?> type = field.getType();
                //创建对象
                try {
                    Object o = type.newInstance();
                    field.set(userController, o);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(userController.getUserService());
    }
}
