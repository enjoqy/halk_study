package org.halk.communication.volatilethread;

/**
 * @Author halk
 * @Date 2020/12/23 16:50
 */
public class M {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
    }
}
