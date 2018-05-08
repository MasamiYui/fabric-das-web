package org.it611;

import org.it611.das.util.MD5Util;
import org.junit.Test;

import java.io.FileNotFoundException;

public class MD5Test {

    @Test
    public void Test(){
        // 原文
        String plaintext = "DingSai";
        //  plaintext = "123456";
        System.out.println("原始：" + plaintext);
        System.out.println("普通MD5后：" + MD5Util.MD5(plaintext));

        // 获取加盐后的MD5值
        String ciphertext = MD5Util.generate(plaintext);
        System.out.println("加盐后MD5：" + ciphertext);
        System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, ciphertext));
        /**
         * 其中某次DingSai字符串的MD5值
         */
        String[] tempSalt = { "c4d980d6905a646d27c0c437b1f046d4207aa2396df6af86", "66db82d9da2e35c95416471a147d12e46925d38e1185c043", "61a718e4c15d914504a41d95230087a51816632183732b5a" };

        for (String temp : tempSalt) {
            System.out.println("是否是同一字符串:" + MD5Util.verify(plaintext, temp));
        }
    }


    @Test
    public void Test2(){
        String plaintext1 = "it611";
        String plaintext2 = "admin";
        String encoded1 = "a7eb6c682925092093d1fd7be38e9554316f05372f61de42";
        String encoded2 ="b82353f81a5100e91c57e77477271fd37927a26128180783";
        System.out.println(MD5Util.verify(plaintext1, encoded1));
        System.out.println(MD5Util.verify(plaintext2, encoded2));

    }

    @Test
    public void Test3() throws FileNotFoundException {

        String file_path = "http://192.168.10.128:8080/group1/M00/00/00/wKgKgFrxRaSAF7CVAANlvPVzKlM67.jpeg";
        System.out.println(MD5Util.md5HashCode(file_path));

    }


}
