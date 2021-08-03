package org.halk.communication.thread_test.c_001;

/**
 * 同步方法与非同步方法允许同步运行
 *
 * @Author halk
 * @Date 2020/12/25 11:35
 */
public class T {

    synchronized void m1() {
        System.out.println("m1  run");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1  end");
    }

    void m2() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }

    public static void main(String[] args) {

        T t = new T();

        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();


    }
}
