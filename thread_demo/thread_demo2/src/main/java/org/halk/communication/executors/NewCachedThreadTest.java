package org.halk.communication.executors;

import java.util.concurrent.*;

/**
 * @Author halk
 * @Date 2020/12/10 15:40
 */
public class NewCachedThreadTest {

    public static void main(String[] args) {

        /**
         * 创建一个可缓存的线程池，但阿里巴巴不建议这么干，线程执行完任务后被回收然后被服用
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 30; i++) {
            int index = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " ---> " + index);
            });
        }

        executorService.shutdown();

        /**
         * 阿里巴巴建议手动创建线程池，并指定参数
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                10,
                20,
                1000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 3; i++) {
            pool.execute(new ThreadTask());
        }
        pool.shutdown();

    }
}
