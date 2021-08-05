package org.halk.communication.stopthread;

/**
 * @Author halk
 * @Date 2020/12/9 15:23
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ThreadStop threadStop1 = new ThreadStop();
        ThreadStop threadStop2 = new ThreadStop();
        threadStop1.start();
        threadStop2.start();


        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println("main   ---> " + i);

            if (i == 8) {
                //使用一个标签标记，用判断条件终止线程
//                threadStop1.stopThread();
//                threadStop2.stopThread();

                //中断这个线程
                threadStop1.interrupt();
                threadStop2.interrupt();
            }

        }
        //stop停止不安全，已经不建议使用
//        threadStop.stop();

    }
}
