package org.halk.communication.thread_test.c_004;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 比较atomic、sync、longAdder在高并发下的效率
 * 经过简单验证，longAdder效率最高，因为它使用了分段锁机制（也是乐观锁cas实现的方式之一），sync与比较atomic在不同的数量级下有着不同的表现
 * 线程数量少一点的atomic占优，线程超过1000朝上sync占优，也跟被锁的代码执行的时间有关系
 *
 * cas（atomic、longAdder都是自旋）自旋占用率cpu较高，sync较低
 *
 * @Author halk
 * @Date 2020/12/30 15:11
 */
public class AtomicVsSyncVsLongAdder {

    static long count1 = 0L;
    static AtomicLong count2 = new AtomicLong(0);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) {
        Thread[] threads = new Thread[100000];

        //每个线程内部执行递增的次数
        int degree = 10000;


        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < degree; j++) {
                    synchronized (lock) {
                        count1++;
                    }
                }
            });
        }

        long start = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        long end = System.currentTimeMillis();

        System.out.println("sync --->" + count1 + "--->" + (end - start));

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < degree; j++) {
                    count2.incrementAndGet();
                }
            });
        }

        start = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();

        System.out.println("atomic --->" + count2.get() + "--->" + (end - start));


        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < degree; j++) {
                    count3.increment();
                }
            });
        }

        start = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();

        System.out.println("longAdder --->" + count3.longValue() + "--->" + (end - start));


    }
}
