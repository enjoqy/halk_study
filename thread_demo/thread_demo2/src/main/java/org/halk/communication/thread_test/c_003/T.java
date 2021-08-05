package org.halk.communication.thread_test.c_003;

/**
 * 程序锁定资源运行期间，如果出现异常，锁会被释放掉（可以用catch捕获异常，继续运行）
 *
 * @Author halk
 * @Date 2020/12/25 16:07
 */
public class T {

    private int count = 0;

    synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " start");
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + "\t" + count);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5) {
                //抛出异常，第一个线程释放锁，第二个线程进来继续执行
                //如果捕获异常，第一个线程就不会释放锁
                int a = 1 / 0;
            }

            if (count == 10) {
                return;
            }
        }
    }

    public static void main(String[] args) {

        T t = new T();

        new Thread(t::m1, "t1").start();


        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(t::m1, "t2").start();
    }
}
