package org.halk.communication.threadlocal;

/**
 * @Author halk
 * @Date 2020/12/10 15:06
 */
public class Test {
    public static void main(String[] args) {

        /**
         * 由于线程之间的共享内存，会导致数据出现错乱，出现线程安全问题，这个时候需要本地变量
         */
        Res res = new Res();
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo(res);
        ThreadLocalDemo threadLocalDemo2 = new ThreadLocalDemo(res);
        ThreadLocalDemo threadLocalDemo3 = new ThreadLocalDemo(res);
        threadLocalDemo.start();
        threadLocalDemo2.start();
        threadLocalDemo3.start();
    }
}
