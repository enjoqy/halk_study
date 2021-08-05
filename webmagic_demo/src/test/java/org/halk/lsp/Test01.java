package org.halk.lsp;

import org.junit.Test;

/**
 * @Author halk
 * @Date 2021/1/14 21:56
 */
public class Test01 {

    @Test
    public void test01() {
        String str = "https://www.tupianzj.com//meinv/20200629/212861_10.html";

        String[] split1 = str.split("\\/");
        String substring = str.substring(0, str.length() - str.split("\\/")[split1.length - 1].length());
        System.out.println(substring);
        for (int i = 0; i < split1.length; i++) {
            System.out.println(split1[i]);
        }
    }

    @Test
    public void test02() {
        String str = "hhhhhhhhhhhhhhdfddf.jpg";
        System.out.println(str.substring(str.length() - 4));

        str = "https://www.tupianzj.com/meinv/yishu/list_178_1.html";
        String substring = str.substring(0, str.lastIndexOf("/") + 1);
        System.out.println(substring);


    }
}
