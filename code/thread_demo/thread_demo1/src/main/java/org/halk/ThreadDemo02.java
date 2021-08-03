package org.halk;

/**
 * 创建线程的几种方式
 * 方式1：继承Thread类，重写run方法
 *
 * @Author halk
 * @Date 2020/12/7 14:14
 */
public class ThreadDemo02 {

    /**
     * 程序启动时，主线程的循环与子线程的循环会交叉打印，原因是cpu随机切换时会有时间差
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("创建线程开始 main");
        //1.定义一个类，继承Thread类，重写run方法
        //2.启动线程
        CreateThread createThread = new CreateThread();
        createThread.start();
        System.out.println("线程已启动 main");
        for (int i = 0; i < 50; i++) {
            System.out.println("main() = " + i);
        }
    }
}

class CreateThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("run() = " + i);
        }
    }
}

