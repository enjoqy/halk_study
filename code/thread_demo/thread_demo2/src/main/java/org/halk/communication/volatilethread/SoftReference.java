package org.halk.communication.volatilethread;

/**
 * 软引用，
 *
 * @Author halk
 * @Date 2020/12/23 17:01
 */
public class SoftReference {

    //程序运行时指定堆空间为20M （-Xmx20M），首先new一个对象用了10M空间（软引用），这时垃圾回收器不会回收，
    // 当在new一个12M的对象时，堆空间装不下就会进行垃圾回收，如果还放不下就会干掉软引用
    public static void main(String[] args) throws InterruptedException {

        //软引用非常适合缓存，内存够的时候就在里面呆着，不够的时候就踢出去
        java.lang.ref.SoftReference<byte[]> softReference = new java.lang.ref.SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(softReference.get());

        System.gc();

        Thread.sleep(500);

        System.out.println(softReference.get());

        byte[] bytes = new byte[1024 * 1024 * 12];

        System.out.println(softReference.get());

    }

}
