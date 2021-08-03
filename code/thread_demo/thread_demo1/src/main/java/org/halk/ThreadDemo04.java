package org.halk;

/**
 * 创建线程的几种方式
 * 方式3：使用匿名内部类实现
 *
 * @Author halk
 * @Date 2020/12/7 16:03
 */
public class ThreadDemo04 {

    public static void main(String[] args) {

        System.out.println("创建线程开始 main");

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("run() = " + i);
            }
        }).start();

        System.out.println("线程已启动 main");
        for (int i = 0; i < 100; i++) {
            System.out.println("main() = " + i);
        }
    }
}
