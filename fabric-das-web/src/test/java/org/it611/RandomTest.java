package org.it611;

import org.junit.Test;

import java.util.Random;

public class RandomTest {

    @Test
    public void Test1(){
        int degreeCertNumTotal = new Random().nextInt(21)+ 100;
        int videoNumTotal = new Random().nextInt(21)+ 100;
        int audioNumTotal = new Random().nextInt(21)+ 100;
        int photoNumTotal = new Random().nextInt(21)+ 100;
        System.out.println(degreeCertNumTotal);
        System.out.println(videoNumTotal);
        System.out.println(audioNumTotal);
        System.out.println(photoNumTotal);
    }
}
