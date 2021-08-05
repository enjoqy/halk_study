package org.halk.communication.mylock;

/**
 * @Author halk
 * @Date 2020/12/8 21:39
 */
public class OutThread extends Thread {

    private Res res;

    public OutThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            try {
                res.getLock().lock();
                if (!res.isFlag()) {
                    res.getCondition().await();
                }
                System.out.println(res.getUsername() + "---> " + res.getSex());
                res.setFlag(false);
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
