package org.halk;

/**
 * 创建线程的几种方式
 * * 方式2：实现runnable接口，重写run方法
 *
 * @Author halk
 * @Date 2020/12/7 15:45
 */
public class ThreadDemo03 {

    public static void main(String[] args) {
        System.out.println("创建线程开始 main");

        CreateRunnable createRunnable = new CreateRunnable();
        Thread thread = new Thread(createRunnable);
        thread.start();

        System.out.println("线程已启动 main");
        for (int i = 0; i < 50; i++) {
            System.out.println("main() = " + i);
        }
    }
}

class CreateRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("run() = " + i);
        }
    }
}
