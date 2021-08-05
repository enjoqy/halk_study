package org.halk.communication.jointhread;

/**
 * 问题：现有t1 t2 t3 三个线程，需要保证线程t1最先执行完毕，t3最后执行完毕
 *
 * @Author halk
 * @Date 2020/12/9 16:35
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " --> " + i);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " --> " + i);
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " --> " + i);
            }
        }, "t3");

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
    }
}
