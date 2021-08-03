package org.halk.communication.jointhread;

/**
 * @Author halk
 * @Date 2020/12/9 16:16
 */
public class JoinThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " ---> " + i);
        }
    }
}
