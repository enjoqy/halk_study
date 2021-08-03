package org.halk.communication.volatilethread;

/**
 * @Author halk
 * @Date 2020/12/10 10:55
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
//        volatileThreadTest();


        volatileNoAtomic();
    }

    /**
     * @Author halk
     * @Description 测试volatile关键字是非原子性的 （不能保证线程的同步）
     * @Date 2020/12/10 10:58
     * @Param []
     * @return void
     **/
    public static void volatileNoAtomic() {
        VolatileNoAtomic[] volatileNoAtomics = new VolatileNoAtomic[10];
        for (int i = 0; i < volatileNoAtomics.length; i++) {
            volatileNoAtomics[i] = new VolatileNoAtomic();
        }
        for (int i = 0; i < volatileNoAtomics.length; i++) {
            volatileNoAtomics[i].start();
        }
    }

    /**
     * volatile关键字让变量在多个线程之间可见，不使用这个关键字，修改的flag值无法同步到主内存中，线程就无法停下来
     * <p>
     * 原因是线程之间不可见，读取的是副本，没有及时读取主内存的结果
     * 解决办法是使用volatile关键字解决线程之间可见性问题，强制线程每次读取值的时候到主内存读取
     *
     * @throws InterruptedException
     */
    public static void volatileThreadTest() throws InterruptedException {
        VolatileThread volatileThread = new VolatileThread();
        volatileThread.start();

        Thread.sleep(300);

        volatileThread.isFlag(false);
    }
}
