package org.halk.communication.volatilethread;

/**
 * cpu会乱序执行，实际上是顺序执行，但是执行时间短的会先一步执行完成，看起来就是乱序
 * <p>
 * 下面这段程序就是证明的例子，正常情况下 x,y 的值只可能是 0，1 或者 1，0，当出现0，0时，就代表cpu没有按照代码的顺序执行
 *
 * @Author halk
 * @Date 2020/12/22 16:30
 */
public class Disorder {

    static int a, b, x, y;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;

        for (; ; ) {
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread two = new Thread(() -> {
                b = 1;
                y = a;
            });

            one.start();
            two.start();
            one.join();
            two.join();
            String result = "第 " + i + " 次(" + x + "," + y + ")";

            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                i++;
                System.out.println(result);
            }

        }
    }
}
