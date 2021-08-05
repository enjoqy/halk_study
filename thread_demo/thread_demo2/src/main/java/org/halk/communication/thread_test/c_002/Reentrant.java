package org.halk.communication.thread_test.c_002;

/**
 * synchronized是可重入的，一个线程可以调用另外一个线程，子类与父类同时加锁，子类是可以调用父类的
 *
 * @Author halk
 * @Date 2020/12/25 15:46
 */
public class Reentrant {

    static Object lock = new Object();

    synchronized void m1() {
        System.out.println("m1 start");

        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                System.out.println(i);

                if (i == 3) {
                    m2();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        m2();
        System.out.println("m1 end");
    }

    synchronized void m2() {

        /*synchronized (lock) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("m2 start");
    }

    public static void main(String[] args) {
        Reentrant reentrant = new Reentrant();

        //在m1方法中可以直接调用m2，虽然m2也上了锁，但是同一把锁，因为synchronized是可重入锁同一个线程可以再一次获得相同的锁，所以可以直接调用
        new Thread(reentrant::m1).start();

        //因为用的是同一把锁，m2方法必须等m1方法释放锁，m2获取到锁之后才能执行
//        new Thread(reentrant::m2).start();


    }
}
