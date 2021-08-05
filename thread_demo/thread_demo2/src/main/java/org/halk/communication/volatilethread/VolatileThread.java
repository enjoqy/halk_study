package org.halk.communication.volatilethread;

/**
 * volatile可以办证可见性与禁止指令重排，但不保证原子性，再多线程中依然会出问题
 * @Author halk
 * @Date 2020/12/10 10:03
 */
public class VolatileThread extends Thread {

    private volatile boolean flag = true;

    @Override
    public void run() {
        System.out.println("start");

        while (flag) {
            System.out.println("----------->");
        }

        System.out.println("end");
    }

    public void isFlag(boolean flag) {
        this.flag = flag;
    }
}
