package org.halk.communication.executors;

/**
 * @Author halk
 * @Date 2020/12/10 15:57
 */
public class ThreadTask implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
