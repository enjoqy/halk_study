package org.halk.communication.mysynchronized;

/**
 * @Author halk
 * @Date 2020/12/8 21:39
 */
public class OutThread extends Thread {

    private Res res;

    public OutThread(Res res){
        this.res = res;
    }

    @Override
    public void run() {
        while (true){
           synchronized (res){

               if (res.isFlag()){
                   System.out.println(res.getUsername() + "---> " + res.getSex());
                   res.setFlag(false);
               }

               //notify 唤醒线程，与wait一起使用
               res.notify();
            }
        }
    }
}
