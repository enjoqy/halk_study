package org.halk;

/**
 * sleep，让线程进入休眠状态，但是并不会释放锁
 * wait，可以释放锁
 *
 * @Author halk
 * @Date 2020/12/7 16:34
 */
public class ThreadDemo05 {

    public static void main(String[] args) {
        //开启两个线程
        DemoThread demoThread = new DemoThread();
        demoThread.setName("线程一");
        demoThread.start();
        DemoThread demoThread2 = new DemoThread();
        demoThread2.setName("线程二");
        demoThread2.start();
    }
}

/**
 * this.getId() 与 this.getName() 这两个方法（不限于）只有在继承thread类时才可以使用，实现runnable接口时不可使用
 */
class DemoThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                //sleep作用让当前线程从运行状态变为休眠状态，
                Thread.sleep(1000);
                //获取线程id， id是多线程随机进行分配不重复主键
                System.out.println("getId() = " + this.getId() + " i = " + i);
                System.out.println("getName() = " + this.getName() + " i = " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
