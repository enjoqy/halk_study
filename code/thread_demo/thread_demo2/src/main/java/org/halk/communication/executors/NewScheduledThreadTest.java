package org.halk.communication.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时的线程池
 *
 * @Author halk
 * @Date 2020/12/11 10:23
 */
public class NewScheduledThreadTest {

    public static void main(String[] args) {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        pool.schedule(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "---> " + i);
            }
        }, 500, TimeUnit.MILLISECONDS);

        new Thread().start();
    }
}
