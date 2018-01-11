package com.dfire.test;

/**
 *
 * @author xiaosuda
 * @date 2017/12/29
 */
public class IntegerTest {

    public static void main(String [] args){


        int countBits = Integer.SIZE - 3;
        int capacity = (1 << countBits) - 1;
        System.out.println(Integer.toBinaryString(capacity));
        System.out.println(Integer.toBinaryString(~capacity));
        System.out.println(Integer.toBinaryString(-1 << countBits));
        for (int i = 0; i < 4; i++) {
            System.out.println(Integer.toBinaryString(i << countBits));
        }
    }
}
