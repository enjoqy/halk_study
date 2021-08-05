import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/1/7 10:57
 */
public class test {

    @Test
    public void test01(){
       String str = "";
        char[] levelCharArray = str.toCharArray();
        int maxLevel = 0;
        for (int i = 0; i < levelCharArray.length-1; i++) {
            if(maxLevel < (int)levelCharArray[i + 1]){
                maxLevel = levelCharArray[i + 1];
            }
        }
        System.out.println(maxLevel);
    }

    public static void main(String[] args) {

        int aa = 0;
        for (int i = 0; i < 10; i++) {
            aa =+ 1;
            System.out.println(aa);
        }
        System.out.println(aa);


        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String[] split = a.split("");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.print(",\"" + split[i] + "\"");
        }

        List<String> AToZList = new ArrayList<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"));
        AToZList.forEach(x -> {
            System.out.println(x);
        });


    }
}
