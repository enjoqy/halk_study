package org.halk.communication.stopthread;

/**
 * @Author halk
 * @Date 2020/12/9 15:21
 */
public class ThreadStop extends Thread {

    private boolean flag = true;


    @Override
    public synchronized void run() {

        while (flag) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                stopThread();
            }
            System.out.println(getName() + " --> 正在运行的子线程    ");
        }
    }

    public void stopThread() {
        this.flag = false;
    }
}
