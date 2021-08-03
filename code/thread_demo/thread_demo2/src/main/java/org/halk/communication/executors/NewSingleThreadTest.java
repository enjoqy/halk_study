package org.halk.communication.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author halk
 * @Date 2020/12/11 10:28
 */
public class NewSingleThreadTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "---> " + index);
            });
        }
    }
}
