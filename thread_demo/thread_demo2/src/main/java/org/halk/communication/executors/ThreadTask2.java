package org.halk.communication.executors;

/**
 * @Author halk
 * @Date 2020/12/10 15:57
 */
public class ThreadTask2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "----" + i);
        }
    }
}
