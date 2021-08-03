package org.halk;

/**
 * 什么是进程？
 * 进程其实是一个应用程序，进程是所有线程的集合
 * <p>
 * 什么是线程？
 * 线程其实就是一条执行路径，main是一条主线程（程序的入口），如果是自己创建的线程则是子线程
 *
 * GC线程是守护线程，专门的垃圾回收器
 *
 * @Author halk
 * @Date 2020/12/7 14:14
 */
public class ThreadDemo01 {


    public static void main(String[] args) {
        System.out.println("this is 主线程");
    }
}
