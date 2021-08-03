package org.halk.demo;

/**
 * 使用多线程模拟抢火车票
 *
 * @Author halk
 * @Date 2020/12/8 11:03
 */
public class ThreadDemo02 {
    public static void main(String[] args) {
        ThreadTrain02 threadTrain = new ThreadTrain02();
        Thread thread = new Thread(threadTrain);
        Thread thread2 = new Thread(threadTrain);
        thread.start();
        thread2.start();
    }
}

/**
 * synchronized() 同步函数
 */
class ThreadTrain02 implements Runnable {

    private int count = 100;

    @Override
    public void run() {
        while (count > 0) {
            show();
        }
    }

    public synchronized void show() {
        if (count > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ", 出售第" + (100 - count + 1) + "张车票");
            count--;
        }
    }
}
