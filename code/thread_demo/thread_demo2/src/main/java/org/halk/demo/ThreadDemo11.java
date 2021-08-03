package org.halk.demo;

class ThreadTrain11 implements Runnable {
    // 定义火车票总数
    int trainCount = 100;
    private Object oj = new Object();
    public boolean flag = true;

    @Override
    public void run() {
        if (flag) {
            while (trainCount > 0) {
                /**
                 * 一般代码执行完毕后，锁会自动释放，让其他线程拿到锁执行
                 * flag -》 true  先拿oj这把锁，再拿this这把锁，代码才可以执行
                 * flag -》 false  先拿this这把锁，再拿oj这把锁，代码才可以执行
                 *
                 */
                synchronized (oj) {
                    show();
                }
            }
        } else {
            while (trainCount > 0) {
                show();
            }
        }

    }

    /**
     * 死锁
     */
    public synchronized void show() {
        //
        synchronized (oj) {
            if (trainCount > 0) {
                try {
                    Thread.sleep(40);
                } catch (Exception e) {

                }
                System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "票");
                trainCount--;
            }
        }
    }

}

/**
 * @classDesc: 功能描述:()
 * @author: 余胜军
 * @createTime: 2017年8月19日 下午6:41:58
 * @version: v1.0
 * @copyright:上海每特教育科技有限公司
 */
public class ThreadDemo11 {
    public static void main(String[] args) throws InterruptedException {
        // 定义一个实例
        ThreadTrain11 threadTrain11 = new ThreadTrain11();
        Thread thread1 = new Thread(threadTrain11, "一号窗口");
        Thread thread2 = new Thread(threadTrain11, "二号窗口");
        thread1.start();
        Thread.sleep(40);
        threadTrain11.flag = false;
        thread2.start();
    }
}
