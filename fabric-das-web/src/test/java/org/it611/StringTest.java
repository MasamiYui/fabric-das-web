package org.it611;

import org.junit.Test;

public class StringTest {

    @Test
    public void Test1() {
        String url = "www.baidu.com";
        String[] arr = url.split(";");
        System.out.println(arr[0]);
        System.out.println(arr.length);
    }
}
