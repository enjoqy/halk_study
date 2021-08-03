package org.halk.communication.mylock;

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

            try {
                //lock 和synchronized类似，lock需要手动获得锁，以及手动解锁
                res.getLock().lock();

                if (res.isFlag()) {
                    //让线程释放锁
                    res.getCondition().await();
                }
                if (count == 0) {
                    res.setUsername("小明");
                    res.setSex("男");
                } else {
                    res.setUsername("红红");
                    res.setSex("女");
                }
                count = (count + 1) % 2;

                res.setFlag(true);
                //唤醒另外一个线程
                res.getCondition().signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                res.getLock().unlock();
            }
        }
    }
}
