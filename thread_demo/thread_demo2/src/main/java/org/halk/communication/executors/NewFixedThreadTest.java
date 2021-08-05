package org.halk.communication.executors;

import java.util.concurrent.*;

/**
 * @Author halk
 * @Date 2020/12/10 16:14
 */
public class NewFixedThreadTest {

    public static void main(String[] args) {

        /**
         * 固定并发数量的线程池
         */
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "---" + index);
            });
        }

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                3,
                3,
                1000,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                (ThreadFactory) Executors.newFixedThreadPool(3),
                //AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作；
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 10; i++) {
            poolExecutor.execute(new ThreadTask2());
        }
    }
}
