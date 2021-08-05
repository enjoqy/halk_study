package org.halk.communication.thread_test.c_002;

/**
 * 子类是可以调用父类的，synchronized修饰方法，实际上锁的是this，是当前类对象
 * @Author halk
 * @Date 2020/12/25 15:59
 */
public class T {

    synchronized void m(){
        System.out.println("T start");
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("T end");
    }

    public static void main(String[] args) {
        TT tt = new TT();
        new Thread(tt::m).start();
    }
}

class TT extends T {

    @Override
    synchronized void m() {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}
