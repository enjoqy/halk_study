package org.halk.communication.volatilethread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author halk
 * @Date 2020/12/10 10:50
 */
public class VolatileNoAtomic extends Thread {

    //    private static volatile int count = 0;
    /**
     * java并发包下的原子类，具有原子性
     */
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
//            count++;
            atomicInteger.incrementAndGet();
        }
        System.out.println(getName() + "--->  " + atomicInteger.get());
    }
}
