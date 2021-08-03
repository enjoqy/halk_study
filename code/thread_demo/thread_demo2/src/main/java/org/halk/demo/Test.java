package org.halk.demo;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author halk
 * @Date 2020/12/8 17:29
 */
public class Test {
    public static void main(String[] args) {
        Object o = new Object();
        //使用工具将实例在内存中的占情况打印出来
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        Object[] a = new Object[1024];
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
