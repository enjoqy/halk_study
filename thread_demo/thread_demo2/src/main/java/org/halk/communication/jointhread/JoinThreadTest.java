package org.halk.communication.jointhread;

import org.junit.Test;

/**
 * @Author halk
 * @Date 2020/12/9 16:16
 */
public class JoinThreadTest {

    public static void main(String[] args) throws InterruptedException {
        JoinThread joinThread = new JoinThread();
        Thread thread = new Thread(joinThread);
        thread.start();

        //调用join（）其他线程需要等待这个线程执行结束之后才可以执行，参数是等待时间，不写参数是等待线程执行完毕
        thread.join(1000);

        for (int i = 0; i < 30; i++) {
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + "  --> " + i);
        }
    }
}
