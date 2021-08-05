package org.halk.spring_ioc;

import org.halk.spring_ioc.service.impl.SysUserServiceImpl;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author halk
 * @Date 2020/12/2 10:42
 */
public class Demo {

    public static void main(String[] args) {

        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                "$Proxy", new Class[]{SysUserServiceImpl.class});

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("userClass.class");
            fileOutputStream.write(proxyClassFile);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
