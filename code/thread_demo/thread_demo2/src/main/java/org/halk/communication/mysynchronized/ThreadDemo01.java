package org.halk.communication.mysynchronized;

/**
 * @Author halk
 * @Date 2020/12/8 21:26
 */
public class ThreadDemo01 {

    public static void main(String[] args) {

        Res res = new Res();
        IpuThread ipuThread = new IpuThread(res);
        OutThread outThread = new OutThread(res);
        ipuThread.start();
        outThread.start();
    }
}
