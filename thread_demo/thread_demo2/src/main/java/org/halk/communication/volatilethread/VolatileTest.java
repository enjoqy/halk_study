package org.halk.communication.volatilethread;

import java.util.ArrayList;

/**
 * volatile可以办证可见性与禁止指令重排，但不保证原子性，再多线程中依然会出问题
 *
 * @Author halk
 * @Date 2020/12/29 14:29
 */
public class VolatileTest {
    int count = 0;

    /**
     * 解决办法就是在这里加锁，或者使用AtomicInteger
     */
    /*synchronized*/ void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(volatileTest::m, "thread-" + i));
        }
        threads.forEach(x -> x.start());

        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(volatileTest.count);

    }

}
