package org.halk.communication.threadlocal;

/**
 * @Author halk
 * @Date 2020/12/10 14:36
 */
public class ThreadLocalDemo extends Thread{

    private Res res;

    public ThreadLocalDemo(Res res){
        this.res = res;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(getName() + " ---> " + i + ", num " + res.getNum());
        }
    }
}
