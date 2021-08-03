package org.halk.communication.volatilethread;

import java.util.concurrent.TimeUnit;

/**
 * 线程本地变量，变量存储于线程本地，其他线程不可访问
 * @Author halk
 * @Date 2020/12/24 10:40
 */
public class ThreadLocalTest {
    volatile static Person p = new Person();
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(p);
            System.out.println(tl.get());
        }).start();
    }

    static class Person{
        String name = "zhangsan";

        @Override
        public String toString() {
            return "name = " + name;
        }
    }

}
