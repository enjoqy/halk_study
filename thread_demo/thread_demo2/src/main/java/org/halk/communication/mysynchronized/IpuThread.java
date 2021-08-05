package org.halk.communication.mysynchronized;

/**
 * 模拟写入线程
 *
 * @Author halk
 * @Date 2020/12/8 21:29
 */
public class IpuThread extends Thread {

    private Res res;

    public IpuThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            synchronized (res) {

                if (res.isFlag()) {
                    try {
                        //wait（）当前线程等待；释放资源（释放锁），需要与synchronized一起使用
                        // sleep可以让当前线程变为休眠状态，但是不是释放资源（不会释放锁），可以单独使用
                        res.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (count == 0) {
                    res.setUsername("小明");
                    res.setSex("男");
                } else {
                    res.setUsername("红红");
                    res.setSex("女");
                }
            }
            count = (count + 1) % 2;

            res.setFlag(true);

        }
    }
}
