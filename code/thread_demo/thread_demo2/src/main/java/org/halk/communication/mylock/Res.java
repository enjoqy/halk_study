package org.halk.communication.mylock;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author halk
 * @Date 2020/12/8 21:27
 */
@Data
public class Res {

    private  String username;
    private String sex;

    /**
     * flag 为false时 res是未赋值的，为true时是已赋值的
     */
    private boolean flag = false;

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();
}
