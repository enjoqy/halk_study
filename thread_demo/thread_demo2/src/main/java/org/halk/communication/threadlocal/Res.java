package org.halk.communication.threadlocal;

import lombok.Data;

/**
 * @Author halk
 * @Date 2020/12/10 14:57
 */
public class Res {

    private int num = 0;

    /*ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };*/

    /**
     * 设置本地局部变量，和其他线程的局部变量隔开，互不影响
     */
    private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    /**
     * 生成订单号
     * @return
     */
    public int getNum(){
//        num = num + 1;
        int num = this.threadLocal.get() + 1;
        this.threadLocal.set(num);
        return num;
    }
}
