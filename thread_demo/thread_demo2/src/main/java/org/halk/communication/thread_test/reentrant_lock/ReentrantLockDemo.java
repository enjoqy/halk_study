package org.halk.communication.thread_test.reentrant_lock;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，需要手动加锁与解锁
 * tryLock可以尝试获取锁，获取不到的时候就啥事都不干
 * 公平锁：线程进来之后看一下是否有等待的队列，如果有就加入到队列的后面等待
 * 非公平锁：线程进来之后不管是否有等待的队列，直接参与抢占cpu资源
 * synchronized只有非公平锁，Lock可以选公平或者非公平锁
 * @Author halk
 * @Date 2020/12/31 9:50
 */
public class ReentrantLockDemo {

    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        try {
            lock.lock();
            System.out.println("m2 start ------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m3(){
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m3 --- " + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo lockDemo = new ReentrantLockDemo();
        new Thread(lockDemo::m1, "m1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 用的是同一把锁，m2必须等m1释放锁之后才能执行
         */
        new Thread(lockDemo::m2, "m2").start();
        new Thread(lockDemo::m3, "m3").start();


    }
}
