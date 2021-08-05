package org.junhi.eureka;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

/**
 * @Author halk
 * @Date 2019/12/24 9:03
 */
public class TranscodingTest {

    /**
     * @Author halk
     * @Description char数组转为字符串
     * @Date 2019/12/24 10:28
     * @Param []
     * @return void
     **/
    @Test
    public void test01(){
        byte[] b={'2','2','4','6'};
        System.out.println(new String(b));
        System.out.println(new String(b, 1,2));
    }

    /**
     * @Author halk
     * @Description  将编码转为utf8 实际上是unicode转utf8
     * @Date 2019/12/24 10:28
     * @Param []
     * @return void
     **/
    @Test
    public void test02() throws UnsupportedEncodingException {
        String str = "\u5C06\u6765\u4F5C\u4E3A\u5FAE\u670D\u52A1\u540D\u79F0\u6CE8\u5165\u5230\u5BB9\u5668";

        String utf8 = new String(str.getBytes("utf8"), "utf8");
        System.out.println(utf8);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "简介";
        String tt = gbEncoding(s);
        System.out.println(decodeUnicode(tt));

        String str = "\u5C06\u6765\u4F5C\u4E3A\u5FAE\u670D\u52A1\u540D\u79F0\u6CE8\u5165\u5230\u5BB9\u5668";
        System.out.println(decodeUnicode(str));
    }


    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
}
