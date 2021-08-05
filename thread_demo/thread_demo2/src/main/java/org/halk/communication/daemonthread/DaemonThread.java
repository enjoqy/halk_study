package org.halk.communication.daemonthread;

/**
 * java中的线程分为主线程（main），子线程（用户线程），守护线程（例如gc）
 * <p>
 * 守护线程（后台线程）:在一个进程中如果只剩下 了守护线程，那么守护线程也会死亡。
 *
 * @Author halk
 * @Date 2020/12/9 16:02
 */
public class DaemonThread {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("i am is zi xian cheng " + i);
            }
        });

        //守护线程（后台线程）:在一个进程中如果只剩下 了守护线程，那么守护线程也会死亡。
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("i am is zhu xian cheng " + i);
        }

        System.out.println("----end");
    }
}
