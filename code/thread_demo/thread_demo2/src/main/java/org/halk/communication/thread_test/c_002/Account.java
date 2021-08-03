package org.halk.communication.thread_test.c_002;

/**
 * 模拟线程之间造成脏读的情况
 * 解决方法是对读加锁，但是加锁会造成效率低下，如果业务允许脏读就不要加锁
 *
 * @Author halk
 * @Date 2020/12/25 11:44
 */
public class Account {

    private String name;
    private double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public synchronized void get() {
        System.out.println("name = " + this.name + " balance = " + balance);
    }

    public static void main(String[] args) {
        Account account = new Account();

        new Thread(() -> {
            account.set("honghong", 100);
        }).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        account.get();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        account.get();
    }

}
