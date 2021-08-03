package org.halk.communication.volatilethread;

import java.io.IOException;

/**
 * java中四种引用，强弱软虚，此为强引用
 * <p>
 * 栈空间中的m指向堆空间的 M的实例，当给m赋值为null时，就没有引用指向M的实例了，垃圾回收器就会回收它
 *
 * @Author halk
 * @Date 2020/12/23 16:48
 */
public class NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc();

        System.in.read();
    }
}
