package org.halk.demo;

/**
 * 使用多线程模拟抢火车票
 *
 * @Author halk
 * @Date 2020/12/8 11:03
 */
public class ThreadDemo01 {
    public static void main(String[] args) {
        ThreadTrain threadTrain = new ThreadTrain();
        Thread thread = new Thread(threadTrain);
        Thread thread2 = new Thread(threadTrain);
        thread.start();
        thread2.start();
    }
}

/**
 * synchronized() 同步代码块
 */
class ThreadTrain implements Runnable {

    private int count = 100;

    @Override
    public void run() {
        try {
            while (count > 0) {
                Thread.sleep(50);
                synchronized (this) {
                    if (count > 0) {
                        System.out.println(Thread.currentThread().getName() + ", 出售第" + (100 - count + 1) + "张车票");
                        count--;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
